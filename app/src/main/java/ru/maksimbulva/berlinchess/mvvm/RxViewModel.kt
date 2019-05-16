package ru.maksimbulva.berlinchess.mvvm

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

abstract class RxViewModel : ViewModel() {

    private val disposables = mutableListOf<Disposable>()

    override fun onCleared() {
        super.onCleared()
        disposables.forEach { if (!it.isDisposed) it.dispose() }
    }

    protected fun addSubscription(disposable: Disposable) {
        disposables.add(disposable)
    }
}
