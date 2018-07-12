package com.chrynan.launcher.ui.fragment

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrynan.launcher.R
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.navigator.HomeNavigator
import com.chrynan.launcher.presenter.HomePresenter
import com.chrynan.launcher.ui.BottomSheetAlphaCallback
import com.chrynan.launcher.ui.BottomSheetCallbackGroup
import com.chrynan.launcher.ui.view.HomeView
import com.chrynan.launcher.util.setOfNotNull
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_app_bottom_sheet.*
import javax.inject.Inject

class HomeFragment : BaseFragment(),
        HomeView,
        HomeNavigator {

    companion object {

        fun newInstance() = HomeFragment()
    }

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomSheet?.setupBottomSheet()
    }

    override fun onResume() {
        super.onResume()

        presenter.getItems()
    }

    private fun View.setupBottomSheet() {
        BottomSheetBehavior.from(this).apply {
            dockView?.post {
                peekHeight = (dockScrollIndicatorView?.measuredHeight
                        ?: 0) + (dockView?.measuredHeight ?: 0)
            }

            val alphaCallback = BottomSheetAlphaCallback(
                    isInitiallyCollapsed = true,
                    collapsedVisibleViews = listOf(dockView, dockScrollIndicatorView),
                    expandedVisibleViews = listOf(appListFragmentContainer))

            setBottomSheetCallback(BottomSheetCallbackGroup(setOfNotNull(alphaCallback, dockScrollIndicatorView?.bottomSheetIndicatorScrollCallback)))
        }
    }

    override fun showFolder(folder: LauncherItem.Folder) {
        launcher?.launcherItem = folder
    }

    override fun showShortcut(shortcut: LauncherItem.SingleItem.Shortcut) {
        launcher?.launcherItem = shortcut
    }
}