package com.marshallaf.domain.interactor

import com.marshallaf.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<in Params> constructor(
    private val postExecutionThread: PostExecutionThread
) {

  private val disposables = CompositeDisposable()

  protected abstract fun buildUseCaseCompletable(params: Params? = null): Completable

  open fun execute(observer: DisposableCompletableObserver, params: Params? = null) {
    addDisposable(buildUseCaseCompletable(params)
        .subscribeOn(Schedulers.io())
        .observeOn(postExecutionThread.scheduler)
        .subscribeWith(observer)
    )
  }

  private fun addDisposable(disposable: Disposable) {
    disposables.add(disposable)
  }
}
