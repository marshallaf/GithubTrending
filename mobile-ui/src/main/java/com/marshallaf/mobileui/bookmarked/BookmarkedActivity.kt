package com.marshallaf.mobileui.bookmarked

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.marshallaf.mobileui.R
import com.marshallaf.mobileui.injection.ViewModelFactory
import com.marshallaf.mobileui.mapper.ProjectViewMapper
import com.marshallaf.mobileui.model.UiProject
import com.marshallaf.presentation.BrowseBookmarkedProjectsViewModel
import com.marshallaf.presentation.model.ProjectModel
import com.marshallaf.presentation.state.Resource
import com.marshallaf.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_bookmarked.*
import javax.inject.Inject

class BookmarkedActivity : AppCompatActivity() {

  @Inject lateinit var adapter: BookmarkedAdapter
  @Inject lateinit var mapper: ProjectViewMapper
  @Inject lateinit var viewModelFactory: ViewModelFactory
  @Inject lateinit var viewModel: BrowseBookmarkedProjectsViewModel

  companion object {
    fun getStartIntent(context: Context) = Intent(context, BookmarkedActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState, persistentState)

    setContentView(R.layout.activity_bookmarked)

    viewModel = ViewModelProviders.of(this, viewModelFactory)
        .get(BrowseBookmarkedProjectsViewModel::class.java)

    setupBrowseRecycler()
  }

  private fun setupBrowseRecycler() {
    recycler_projects.let {
      it.layoutManager = LinearLayoutManager(this)
      it.adapter = adapter
    }
  }

  override fun onStart() {
    super.onStart()
    viewModel.getProjects()
        .observe(this, Observer {
          it?.let { handleDataState(it) }
        })
    viewModel.fetchProjects()
  }

  private fun handleDataState(resource: Resource<List<ProjectModel>>) {
    when (resource.status) {
      ResourceState.SUCCESS -> {
        setUpScreenForSuccess(resource.data?.map {
          mapper.mapToView(it)
        })
        progress_bar.visibility = View.GONE
        recycler_projects.visibility = View.VISIBLE
      }
      ResourceState.LOADING -> {
        recycler_projects.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
      }
    }
  }

  private fun setUpScreenForSuccess(projects: List<UiProject>?) {
    projects?.let {
      adapter.projects = it
      adapter.notifyDataSetChanged()
    } ?: run {

    }
  }
}
