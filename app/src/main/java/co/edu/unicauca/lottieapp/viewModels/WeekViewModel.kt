package co.edu.unicauca.calendarweekview

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.edu.unicauca.calendarweekview.models.MyEvent
import java.text.SimpleDateFormat
import java.util.*

class weekViewModel : ViewModel() {

    private var _events = MutableLiveData<List<MyEvent>>();

    var eventos: MutableLiveData<List<MyEvent>> = getInicialEvents()

    fun recorrerArray(lista: MutableList<MyEvent>){
        for (item in MyEvent.events) {
            println("recorrer: "+item.title)
        }
    }

    open fun getInicialEvents():MutableLiveData<List<MyEvent>>{
        _events.value = MyEvent.events
        return _events
    }

    fun modifyEvents(parEvents: MutableList<MyEvent>,event: MyEvent):MutableLiveData<List<MyEvent>>{
        parEvents.add(event)
        recorrerArray(parEvents)
        _events.value = parEvents
        eventos = _events
        return eventos
    }

    fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }
}