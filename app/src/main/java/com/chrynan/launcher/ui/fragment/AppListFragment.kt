package com.chrynan.launcher.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrynan.launcher.BR
import com.chrynan.launcher.R
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.navigator.AppListNavigator
import com.chrynan.launcher.presenter.AppListPresenter
import com.chrynan.launcher.ui.adapter.AppListAdapter
import com.chrynan.launcher.ui.adapter.AppListSearchAdapter
import com.chrynan.launcher.ui.adapter.core.AdapterViewTypes
import com.chrynan.launcher.ui.adapter.core.ManagerAdapter
import com.chrynan.launcher.ui.grid.DynamicSpanGridLayoutManager
import com.chrynan.launcher.ui.view.AppListView
import com.chrynan.launcher.util.virtualNavigationBarHeight
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_app_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AppListFragment : BaseFragment(),
        AppListView,
        AppListNavigator,
        AppListAdapter.ClickListener,
        AppListSearchAdapter.ClickListener {

    companion object {

        private const val DEBOUNCE_INPUT_MILLISECONDS = 250L
    }

    override val listenerVariableId: Int = BR.listener

    @Inject
    lateinit var presenter: AppListPresenter

    @Inject
    lateinit var appAdapter: ManagerAdapter

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_app_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.apply {
            layoutManager = DynamicSpanGridLayoutManager(context, mapOf(
                    AdapterViewTypes.APP_LIST to DynamicSpanGridLayoutManager.DEFAULT_ITEM_SPAN_COUNT,
                    AdapterViewTypes.APP_SEARCH to DynamicSpanGridLayoutManager.DEFAULT_MAX_SPAN_COUNT
            ))

            setHasFixedSize(true)

            adapter = appAdapter

            setPaddingRelative(0, 0, 0, paddingBottom + context.virtualNavigationBarHeight)
        }

        searchEditText?.let {
            compositeDisposable.add(
                    it.textChanges()
                            .debounce(DEBOUNCE_INPUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                            .flatMapCompletable { presenter.search(it) }
                            .subscribe({},
                                    { logError(it, "Error listening to text changes.") })
            )
        }

        presenter.getAllApplications()
    }

    override fun onResume() {
        super.onResume()

        presenter.getAllApplications()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()

        presenter.detach()
    }

    override fun showList() {
        recyclerView?.visibility = View.VISIBLE
    }

    override fun hideList() {
        recyclerView?.visibility = View.GONE
    }

    override fun showInput() {
        searchEditText?.visibility = View.VISIBLE
    }

    override fun hideInput() {
        searchEditText?.visibility = View.GONE
    }

    override fun showLoadingIndicator() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        progressBar?.visibility = View.GONE
    }

    override fun showNoResults() {
        // TODO
    }

    override fun hideNoResults() {
        // TODO
    }

    override fun showNoApps() {
        // TODO
    }

    override fun hideNoApps() {
        // TODO
    }

    override fun goToApp(launchIntent: Intent) {
        startActivity(launchIntent)
    }

    override fun goToGooglePlayStoreWithSearch(search: String) {
        // TODO Note this could crash if there is no google play store app installed on the device
        // Also, this may open a dialog chooser for the application
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=$search&c=apps")))
    }

    override fun onAppItemClick(app: LauncherItem.SingleItem.App) {
        goToApp(app.launchIntent)
    }

    override fun onSearchButtonClick() {
        searchEditText?.let { goToGooglePlayStoreWithSearch(it.text.toString()) }
    }
}