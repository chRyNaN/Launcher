package com.chrynan.launcher.model

sealed class AppListState

object LoadingState : AppListState()

object ListState : AppListState()

object NoAppsState : AppListState()

object NoResultsState : AppListState()