package com.marshallaf.mobileui.browse

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marshallaf.mobileui.R
import com.marshallaf.mobileui.injection.module.GlideApp
import com.marshallaf.mobileui.model.UiProject
import javax.inject.Inject

class BrowseAdapter @Inject constructor() : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

  var projects: List<UiProject> = arrayListOf()
  var projectListener: ProjectListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.item_project, parent, false)
    return ViewHolder(itemView)
  }

  override fun getItemCount() = projects.count()

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val project = projects[position]
    holder.ownerNameText.text = project.ownerName
    holder.projectNameText.text = project.name

    GlideApp.with(holder.itemView.context)
        .load(project.ownerAvatar)
        .circleCrop()
        .into(holder.avatarImage)

    val starResource = if (project.isBookmarked) {
      R.drawable.ic_star_black_24dp
    } else {
      R.drawable.ic_star_border_black_24dp
    }
    holder.bookmarkedImage.setImageResource(starResource)

    holder.itemView.setOnClickListener {
      if (project.isBookmarked) {
        projectListener?.onBookmarkedProjectClicked(project.id)
      } else {
        projectListener?.onProjectClicked(project.id)
      }
    }
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var avatarImage: ImageView
    var ownerNameText: TextView
    var projectNameText: TextView
    var bookmarkedImage: ImageView

    init {
      avatarImage = view.findViewById(R.id.image_owner_avatar)
      ownerNameText = view.findViewById(R.id.text_owner_name)
      projectNameText = view.findViewById(R.id.text_project_name)
      bookmarkedImage = view.findViewById(R.id.image_bookmarked)
    }
  }
}