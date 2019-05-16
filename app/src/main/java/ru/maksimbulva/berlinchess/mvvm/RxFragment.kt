package ru.maksimbulva.berlinchess.mvvm

import androidx.fragment.app.Fragment
import io.reactivex.disposables.Disposable

abstract class RxFragment : Fragment() {

    private val disposables = mutableListOf<Disposable>()

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.forEach { if (!it.isDisposed) it.dispose() }
    }

    protected fun addSubscription(disposable: Disposable) {
        disposables.add(disposable)
    }
}