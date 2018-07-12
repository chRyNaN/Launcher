package com.chrynan.launcher.usecase

import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger
import com.chrynan.launcher.model.AppListViewModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SearchForApplicationByNameUseCase @Inject constructor(
        private val getAllAppListViewModelsUseCase: GetAllAppListViewModelsUseCase,
        private val logger: Logger
) : UseCase<CharSequence, Single<List<AppListViewModel>>>,
        Loggable by logger {

    override fun execute(param: CharSequence): Single<List<AppListViewModel>> =
            getAllAppListViewModelsUseCase.execute()
                    .flatMap {
                        if (param.isNotBlank()) {
                            Observable.fromIterable(it)
                                    .filter { it.app.name.contains(other = param, ignoreCase = true) }
                                    .toList()
                        } else {
                            Single.just(it)
                        }
                    }
                    .doOnError { logError(it, "Error searching for ${GetAllAppListViewModelsUseCase::class.java.name} for input $param in ${SearchForApplicationByNameUseCase::class.java.name}.") }
}