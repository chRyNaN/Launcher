package com.chrynan.launcher.usecase

import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger
import com.chrynan.launcher.mapper.AppListViewModelMapper
import com.chrynan.launcher.model.AppListViewModel
import com.chrynan.launcher.util.mapEachFromEmittedList
import io.reactivex.Single
import javax.inject.Inject

class GetAllAppListViewModelsUseCase @Inject constructor(
        private val getAllApplicationsUseCase: GetAllApplicationsUseCase,
        private val viewModelMapper: AppListViewModelMapper,
        private val logger: Logger
) : NoParamUseCase<Single<List<AppListViewModel>>>,
        Loggable by logger {

    override fun execute(param: Nothing?): Single<List<AppListViewModel>> =
            getAllApplicationsUseCase.execute()
                    .mapEachFromEmittedList(viewModelMapper::map)
                    .doOnError { logError(it, "Error retrieving all ${AppListViewModel::class.java.name}s in ${GetAllAppListViewModelsUseCase::class.java.name}.") }
}