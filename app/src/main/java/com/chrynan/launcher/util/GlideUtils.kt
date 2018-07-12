package com.chrynan.launcher.util

import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Drawable
import android.os.Build.VERSION_CODES.N_MR1
import android.support.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.Registry
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface DrawableFunction : (() -> Drawable) {

    val uniqueKey: Key

    val function: () -> Drawable

    override fun invoke() = function()
}

class DrawableFunctionLoader<T : DrawableFunction> : ModelLoader<T, Drawable> {

    override fun buildLoadData(model: T, width: Int, height: Int, options: Options): ModelLoader.LoadData<Drawable>? =
            ModelLoader.LoadData(model.uniqueKey, DrawableFunctionDataFetcher(model))

    override fun handles(model: T) = true
}

class DrawableFunctionDataFetcher(private val function: DrawableFunction) : DataFetcher<Drawable> {

    override fun getDataClass() = Drawable::class.java

    override fun cleanup() {}

    override fun getDataSource() = DataSource.LOCAL

    override fun cancel() {}

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in Drawable>) {
        try {
            val drawable = function()

            callback.onDataReady(drawable)
        } catch (e: Exception) {
            callback.onLoadFailed(e)
        }
    }
}

class DrawableFunctionLoaderFactory<T : DrawableFunction> : ModelLoaderFactory<T, Drawable> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<T, Drawable> =
            DrawableFunctionLoader()

    override fun teardown() {}
}

class PackageDrawableFunction(
        private val packageManager: PackageManager,
        private val packageName: String
) : DrawableFunction {

    companion object {

        private const val PACKAGE_ID_PREFIX = "PackageName:"
    }

    override val uniqueKey: Key
        get() = ObjectKey("$PACKAGE_ID_PREFIX$packageName")

    override val function: () -> Drawable
        get() = { packageManager.getApplicationIcon(packageName) }
}

@RequiresApi(N_MR1)
class ShortcutDrawableFunction(
        private val context: Context,
        private val launcherApps: LauncherApps,
        private val shortcutInfo: ShortcutInfo
) : DrawableFunction {

    companion object {

        private const val SHORTCUT_ID_PREFIX = "ShortcutId:"
    }

    override val uniqueKey: Key
        get() = ObjectKey("$SHORTCUT_ID_PREFIX${shortcutInfo.id}")

    override val function: () -> Drawable
        get() = { launcherApps.getShortcutIconDrawable(shortcutInfo, context.resources.displayMetrics.densityDpi) }
}

@GlideModule
class LauncherAppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(PackageDrawableFunction::class.java, Drawable::class.java, DrawableFunctionLoaderFactory())
        registry.prepend(ShortcutDrawableFunction::class.java, Drawable::class.java, DrawableFunctionLoaderFactory())
    }
}

interface DrawableListener {

    fun onError()

    fun onSuccess(drawable: Drawable)
}

class DrawableRequestListener(private val listener: DrawableListener) : RequestListener<Drawable> {

    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
        listener.onError()

        return false
    }

    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        resource?.let { listener.onSuccess(resource) } ?: listener.onError()

        return false
    }
}

fun RequestBuilder<Drawable>.listener(listener: DrawableListener) = listener(DrawableRequestListener(listener))

fun loadDrawablesTogether(requestBuilders: List<RequestBuilder<Drawable>>): Single<MutableList<Drawable>> =
        Single.just(requestBuilders.map { it.submit() })
                .observeOn(Schedulers.io())
                .flatMap {
                    Observable.fromIterable(it)
                            .map { it.get() }
                            .toList()
                }