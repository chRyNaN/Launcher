package com.chrynan.launcher.presenter

import com.chrynan.launcher.R
import com.chrynan.launcher.binder.AppListBinder
import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger
import com.chrynan.launcher.model.AppListSearchViewModel
import com.chrynan.launcher.model.state.AppListState
import com.chrynan.launcher.ui.adapter.core.DiffProcessor
import com.chrynan.launcher.ui.adapter.core.ListUpdater
import com.chrynan.launcher.usecase.GetAllAppListViewModelsUseCase
import com.chrynan.launcher.usecase.SearchForApplicationByNameUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppListPresenter @Inject constructor(
        private val binder: AppListBinder,
        private val getAllAppListViewModelsUseCase: GetAllAppListViewModelsUseCase,
        private val searchForApplicationByNameUseCase: SearchForApplicationByNameUseCase,
        private val listUpdater: ListUpdater,
        private val diffProcessor: DiffProcessor,
        private val logger: Logger
) : BasePresenter(),
        Loggable by logger {

    private val googlePlaySearchButtonText by string(R.string.google_play_store_search_button)

    private val compositeDisposable = CompositeDisposable()

    override fun detach() {
        compositeDisposable.clear()
    }

    fun getAllApplications() {
        compositeDisposable.add(
                getAllAppListViewModelsUseCase.execute()
                        .map { diffProcessor.process(it) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { binder.bind(AppListState.LoadingState) }
                        .subscribe({
                            listUpdater.updateItems(it, diffProcessor.newList)
                            binder.bind(if (diffProcessor.newList.isEmpty()) AppListState.NoAppsState else AppListState.ListState)
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
                    .doOnSuccess { binder.bind(if (diffProcessor.newList.isEmpty()) AppListState.NoResultsState else AppListState.ListState) }
                    .doOnError { logError(it, "Error searching for input: $input") }
                    .ignoreElement()
                    .onErrorComplete()
}