package com.chrynan.launcher.presenter

import android.content.Context
import com.chrynan.launcher.R
import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger
import com.chrynan.launcher.model.*
import com.chrynan.launcher.ui.adapter.core.DiffProcessor
import com.chrynan.launcher.ui.adapter.core.ListUpdater
import com.chrynan.launcher.ui.view.AppListView
import com.chrynan.launcher.usecase.GetAllAppListViewModelsUseCase
import com.chrynan.launcher.usecase.SearchForApplicationByNameUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppListPresenter @Inject constructor(
        private val context: Context,
        private val view: AppListView,
        private val getAllAppListViewModelsUseCase: GetAllAppListViewModelsUseCase,
        private val searchForApplicationByNameUseCase: SearchForApplicationByNameUseCase,
        private val listUpdater: ListUpdater,
        private val diffProcessor: DiffProcessor,
        private val logger: Logger
) : Presenter,
        Loggable by logger {

    private val googlePlaySearchButtonText by lazy { context.getString(R.string.google_play_store_search_button) }

    private val compositeDisposable = CompositeDisposable()

    private var fullAppList: List<AdapterViewModel> = emptyList()

    override fun detach() {
        compositeDisposable.clear()
    }

    fun getAllApplications() {
        compositeDisposable.add(
                getAllAppListViewModelsUseCase.execute()
                        .map { diffProcessor.process(it) }
                        .doOnSuccess { fullAppList = diffProcessor.newList }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { view.updateState(LoadingState) }
                        .subscribe({
                            listUpdater.updateItems(it, fullAppList)
                            view.updateState(if (fullAppList.isEmpty()) NoAppsState else ListState)
                        }, { logError(it, "Error retrieving all applications.") })
        )
    }

    fun search(input: CharSequence): Completable =
            searchForApplicationByNameUseCase.execute(input)
                    .map { if (it.isNotEmpty() and input.isNotBlank()) it + AppListSearchViewModel(googlePlaySearchButtonText) else it }
                    .map { diffProcessor.process(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { listUpdater.updateItems(it, diffProcessor.newList) }
                    .doOnSuccess {
                        view.updateState(if (diffProcessor.newList.isEmpty()) NoResultsState else ListState)
                    }
                    .doOnError { logError(it, "Error searching for input: $input") }
                    .toCompletable()
                    .onErrorComplete()
}