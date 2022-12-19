package co.edu.unicauca.calendarweekview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.edu.unicauca.lottieapp.data.model.MyEvent

class weekViewModel : ViewModel() {

    private var _events = MutableLiveData<List<MyEvent>>();

    var eventos: MutableLiveData<List<MyEvent>> = getInicialEvents()

    open fun getInicialEvents():MutableLiveData<List<MyEvent>>{
        _events.value = MyEvent.events
        return _events
    }
}