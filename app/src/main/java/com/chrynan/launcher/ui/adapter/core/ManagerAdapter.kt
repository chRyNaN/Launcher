package com.chrynan.launcher.ui.adapter.core

import android.database.DataSetObserver
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import com.chrynan.launcher.model.AdapterViewModel

class ManagerAdapter(private val adapters: Set<DataBindAdapter>) : RecyclerView.Adapter<ManagerAdapter.DataBindViewHolder>(),
        ListUpdater,
        ListAdapter {

    var items: List<AdapterViewModel> = emptyList()
        private set

    private val listAdapterViewHolders: MutableMap<Int, DataBindViewHolder> = mutableMapOf()

    override fun getItemCount() = items.size

    override fun getItemId(position: Int) = items[position].uniqueId

    override fun getItemViewType(position: Int) = adapters.first { it.handlesViewItem(items[position]) }.viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            adapters.first { it.viewType == viewType }.run {
                DataBindViewHolder(
                        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewResourceId, parent, false),
                        viewModelVariableId = viewModelVariableId,
                        listener = getListener())
            }

    override fun onBindViewHolder(holder: DataBindViewHolder, position: Int) {
        holder.bind(items[position])
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
        val viewType = getItemViewType(position)
        val item = items[position]

        var viewHolder = listAdapterViewHolders[viewType]

        if (viewHolder == null) {
            viewHolder = onCreateViewHolder(parent, viewType)
            listAdapterViewHolders[viewType] = viewHolder
        }

        viewHolder.bind(item)

        return viewHolder.itemView
    }

    override fun isEnabled(position: Int) = true

    override fun areAllItemsEnabled() = true

    override fun registerDataSetObserver(observer: DataSetObserver?) =
            registerAdapterDataObserver(DataSetObserverConnector(observer = observer))

    override fun unregisterDataSetObserver(observer: DataSetObserver?) =
            unregisterAdapterDataObserver(DataSetObserverConnector(observer = observer))

    data class DataBindViewHolder(
            private val binding: ViewDataBinding,
            private val viewModelVariableId: Int,
            private val listener: DataBindAdapter.Listener? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdapterViewModel) {
            binding.setVariable(viewModelVariableId, item)
            listener?.let { binding.setVariable(it.listenerVariableId, it) }
        }
    }
}