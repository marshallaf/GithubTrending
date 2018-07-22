package com.marshallaf.mobileui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_browse.recycler_projects as recyclerProjects

class BrowseActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_browse)
  }

  private fun setUpRecycler() {
    recyclerProjects.layoutManager = LinearLayoutManager(this)
  }
}