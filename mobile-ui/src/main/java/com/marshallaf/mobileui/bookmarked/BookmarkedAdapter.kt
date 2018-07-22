package com.marshallaf.mobileui.bookmarked

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marshallaf.mobileui.R
import com.marshallaf.mobileui.model.UiProject
import javax.inject.Inject

class BookmarkedAdapter @Inject constructor() : RecyclerView.Adapter<BookmarkedAdapter.ViewHolder>() {

  var projects: List<UiProject> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.item_bookmarked_project, parent, false)
    return ViewHolder(itemView)
  }

  override fun getItemCount() = projects.count()

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val project = projects[position]
    holder.ownerNameText.text = project.ownerName
    holder.projectNameText.text = project.fullName

    Glide.with(holder.itemView.context)
        .load(project.ownerAvatar)
        .apply(RequestOptions.circleCropTransform())
        .into(holder.avatarImage)
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var avatarImage: ImageView
    var ownerNameText: TextView
    var projectNameText: TextView

    init {
      avatarImage = view.findViewById(R.id.image_owner_avatar)
      ownerNameText = view.findViewById(R.id.text_owner_name)
      projectNameText = view.findViewById(R.id.text_project_name)
    }
  }
}