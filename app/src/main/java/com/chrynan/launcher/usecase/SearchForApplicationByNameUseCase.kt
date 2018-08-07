package com.chrynan.launcher.usecase

import com.chrynan.launcher.model.AppListViewModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SearchForApplicationByNameUseCase @Inject constructor(private val getAllAppListViewModelsUseCase: GetAllAppListViewModelsUseCase) : BaseUseCase<CharSequence, Single<List<AppListViewModel>>>() {

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