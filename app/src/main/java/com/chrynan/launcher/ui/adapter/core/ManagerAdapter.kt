package com.chrynan.launcher.ui.adapter.core

import android.database.DataSetObserver
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import com.chrynan.launcher.model.AdapterViewModel
import com.chrynan.launcher.util.ViewType

class ManagerAdapter(private val adapters: Set<DelegateAdapter>) : RecyclerView.Adapter<DelegateAdapter.DelegateViewHolder>(),
        ListUpdater,
        ListAdapter {

    override var items: List<AdapterViewModel> = emptyList()
        private set

    private val listAdapterViewHolders: MutableMap<ViewType, DelegateAdapter.DelegateViewHolder> = mutableMapOf()

    override fun getItemCount() = items.size

    override fun getItemId(position: Int) = items[position].uniqueId

    override fun getItemViewType(position: Int) = adapters.first { it.handlesModel(items[position]) }.viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DelegateAdapter.DelegateViewHolder(LayoutInflater.from(parent.context).inflate(adapters.first { it.viewType == viewType }.viewResourceId, parent, false))

    override fun onBindViewHolder(holder: DelegateAdapter.DelegateViewHolder, position: Int) {
        val item = items[position]

        adapters.first { it.handlesModel(item) }.bindModel(item, holder.itemView)
    }

    override fun updateItems(diffResult: DiffUtil.DiffResult, newItems: List<AdapterViewModel>) {
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun isEmpty() = count == 0

    override fun getCount() = itemCount

    override fun getItem(position: Int) = items[position]

    override fun getViewTypeCount() = adapters.distinctBy { it.viewType }.count()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = items[position]
        val adapter = adapters.first { it.handlesModel(item) }
        val viewType = adapter.viewType

        var viewHolder = listAdapterViewHolders[viewType]

        if (viewHolder == null) {
            viewHolder = onCreateViewHolder(parent, viewType)
            listAdapterViewHolders[viewType] = viewHolder
        }

        adapter.bindModel(item, viewHolder.itemView)

        return viewHolder.itemView
    }

    override fun isEnabled(position: Int) = true

    override fun areAllItemsEnabled() = true

    override fun registerDataSetObserver(observer: DataSetObserver?) =
            registerAdapterDataObserver(DataSetObserverConnector(observer = observer))

    override fun unregisterDataSetObserver(observer: DataSetObserver?) =
            unregisterAdapterDataObserver(DataSetObserverConnector(observer = observer))
}