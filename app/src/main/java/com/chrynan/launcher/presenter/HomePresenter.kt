package com.chrynan.launcher.presenter

import android.content.pm.LauncherApps
import android.os.Build
import android.util.Log
import com.chrynan.launcher.repository.ShortcutInfoRepository
import com.chrynan.launcher.ui.view.HomeView
import com.chrynan.launcher.usecase.GetAllAppListViewModelsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val view: HomeView,
        private val getAllAppListViewModelsUseCase: GetAllAppListViewModelsUseCase,
        private val shortcutInfoRepository: ShortcutInfoRepository
) : Presenter {

    @Inject
    lateinit var launcherApps: LauncherApps

    override fun detach() {
        // No - Op
    }

    fun getItems() {
        /*
        getAllAppListViewModelsUseCase.execute()
                .map { it.map { it.app } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showFolder(LauncherItem.Folder(items = it, folderName = "Folder"))
                }, {})*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutInfoRepository.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("Shortcut", "onSuccess: it = $it")
                        it.firstOrNull()?.let {
                            Log.d("Shortcut", "onSuccess: it = $it")
                        }
                    }, {
                        Log.d("Shortcut", "onError: it = $it")
                    })
        }
    }
}