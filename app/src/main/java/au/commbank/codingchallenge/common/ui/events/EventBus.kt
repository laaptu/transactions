package au.commbank.codingchallenge.common.ui.events

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

object EventBus {
    private val bus: MutableLiveData<Event<ClickEvent>> = MutableLiveData()

    fun <T : ClickEvent> postEvent(event: T) {
        bus.value = Event(event)
    }

    fun register(lifecycleOwner: LifecycleOwner, observer: Observer<Event<ClickEvent>>) {
        bus.observe(lifecycleOwner, observer)
    }
}

abstract class ClickEvent