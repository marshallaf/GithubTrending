package com.marshallaf.mobileui.browse

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.marshallaf.mobileui.R
import com.marshallaf.mobileui.bookmarked.BookmarkedActivity
import com.marshallaf.mobileui.injection.ViewModelFactory
import com.marshallaf.mobileui.mapper.ProjectViewMapper
import com.marshallaf.mobileui.model.UiProject
import com.marshallaf.presentation.BrowseProjectsViewModel
import com.marshallaf.presentation.model.ProjectModel
import com.marshallaf.presentation.state.Resource
import com.marshallaf.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

  @Inject lateinit var adapter: BrowseAdapter
  @Inject lateinit var mapper: ProjectViewMapper
  @Inject lateinit var viewModelFactory: ViewModelFactory
  lateinit var viewModel: BrowseProjectsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_bookmarked)

    viewModel = ViewModelProviders.of(this, viewModelFactory)
        .get(BrowseProjectsViewModel::class.java)

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

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_bookmarked -> {
        startActivity(BookmarkedActivity.getStartIntent(this))
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
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