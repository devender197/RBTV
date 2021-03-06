package com.nousguide.android.common.commom

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class CommonLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Timber.tag(CommonLiveEvent::class.java.simpleName)
                .w("Multiple observers registered but only one will be notified of changes.")
        }
        super.observe(
            owner,
            Observer {
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(it)
                }
            }
        )
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        value = null
    }
}
