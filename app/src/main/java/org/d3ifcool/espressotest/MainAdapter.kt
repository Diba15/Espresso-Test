package org.d3ifcool.espressotest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3ifcool.espressotest.data.Mahasiswa
import org.d3ifcool.espressotest.databinding.ItemMainBinding

class MainAdapter(private val handler: ClickHandler) :
    ListAdapter<Mahasiswa, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val selectionIds = ArrayList<Int>()

    @SuppressLint("NotifyDataSetChanged")
    fun toggleSelection(pos: Int) {
        val id = getItem(pos).id
        if (selectionIds.contains(id)) selectionIds.remove(id)
        else selectionIds.add(id)
        notifyDataSetChanged()
    }

    fun getSelection(): List<Int> {
        return selectionIds
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetSelection() {
        selectionIds.clear()
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mahasiswa: Mahasiswa) {
            binding.nimTextView.text = mahasiswa.nim
            binding.namaTextView.text = mahasiswa.nama
            val pos = absoluteAdapterPosition
            itemView.isSelected = selectionIds.contains(mahasiswa.id)
            itemView.setOnClickListener { handler.onClick(pos, mahasiswa) }
            itemView.setOnLongClickListener { handler.onLongClick(pos) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Mahasiswa>() {
            override fun areItemsTheSame(oldItem: Mahasiswa, newItem: Mahasiswa): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Mahasiswa, newItem: Mahasiswa): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface ClickHandler {
        fun onClick(position: Int, mahasiswa: Mahasiswa)
        fun onLongClick(position: Int): Boolean
    }
}