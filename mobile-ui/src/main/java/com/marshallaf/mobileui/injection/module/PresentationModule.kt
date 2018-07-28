package com.marshallaf.mobileui.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.marshallaf.mobileui.injection.ViewModelFactory
import com.marshallaf.presentation.BrowseBookmarkedProjectsViewModel
import com.marshallaf.presentation.BrowseProjectsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

  @Binds
  @IntoMap
  @ViewModelKey(BrowseProjectsViewModel::class)
  abstract fun bindBrowseProjectsViewModel(viewModel: BrowseProjectsViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(BrowseBookmarkedProjectsViewModel::class)
  abstract fun bindBrowseBookmarkedViewModel(viewModel: BrowseBookmarkedProjectsViewModel): ViewModel

  @Binds abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
