package com.chrynan.launcher.usecase

import com.chrynan.launcher.mapper.AppListViewModelMapper
import com.chrynan.launcher.model.AppListViewModel
import com.chrynan.launcher.util.mapEachFromEmittedList
import io.reactivex.Single
import javax.inject.Inject

class GetAllAppListViewModelsUseCase @Inject constructor(
        private val getAllApplicationsUseCase: GetAllApplicationsUseCase,
        private val viewModelMapper: AppListViewModelMapper
) : NoParamUseCase<Single<List<AppListViewModel>>>() {

    override fun execute(param: Nothing?): Single<List<AppListViewModel>> =
            getAllApplicationsUseCase.execute()
                    .mapEachFromEmittedList(viewModelMapper::map)
                    .doOnError { logError(it, "Error retrieving all ${AppListViewModel::class.java.name}s in ${GetAllAppListViewModelsUseCase::class.java.name}.") }
}