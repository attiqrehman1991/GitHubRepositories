package com.example.github.repositories.common.view.repositoryList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.repositories.R
import com.example.github.repositories.common.local.RepositoryLocalModel

class RepositoryListAdapter(
    val list: List<RepositoryLocalModel>,
    private val listener: RepositoryListBehaviour
) : RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val container: View = itemView.findViewById(R.id.container)
        val name: TextView = itemView.findViewById(R.id.name)
        val bookmarkImage: ImageView = itemView.findViewById(R.id.bookmarkImage)
        val description: TextView = itemView.findViewById(R.id.description)
        val author: TextView = itemView.findViewById(R.id.author)

        @SuppressLint("SetTextI18n")
        fun bindData() {
            val repository = list[bindingAdapterPosition]
            name.text = repository.name
            repository.description.apply {
                if (this == null) description.visibility = View.GONE else description.visibility =
                    View.VISIBLE
            }?.let {
                description.text = if (it.length > 150) it.take(150)
                    .plus("...") else description.toString()
            }
//            description.text =
            author.text = repository.owner.author
            bookmarkImage.setImageResource(
                if (repository.isBookMarked)
                    R.drawable.baseline_bookmark_black_24
                else
                    R.drawable.baseline_bookmark_border_black_24
            )
            bookmarkImage.setOnClickListener {
                repository.isBookMarked = !repository.isBookMarked
                notifyItemChanged(bindingAdapterPosition)
                listener.repositoryBookmarkChanged(
                    repositoryId = repository.repositoryId,
                    newStatus = repository.isBookMarked
                )
            }
            container.setOnClickListener {
                listener.repositorySelected(repository = repository)
            }
//            container.setOnClickListener {
//                activity.supportFragmentManager
//                    .beginTransaction()
//                    .add(android.R.id.content, DetailFragment(item))
//                    .addToBackStack("detail")
//                    .commit()
//            }
        }
    }

    interface RepositoryListBehaviour {
        fun repositorySelected(repository: RepositoryLocalModel)
        fun repositoryBookmarkChanged(repositoryId: Int, newStatus: Boolean)
    }
}