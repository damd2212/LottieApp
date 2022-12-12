package co.edu.unicauca.calendarweekview.adapter

import android.widget.Toast
import co.edu.unicauca.calendarweekview.models.MyEvent
import co.edu.unicauca.calendarweekview.weekViewModel
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter() :WeekView.SimpleAdapter<MyEvent>() {

    private var modelWeek : weekViewModel = weekViewModel()

    override fun onCreateEntity(item: MyEvent): WeekViewEntity {
        return WeekViewEntity.Event.Builder(item)
            .setId(item.id)
            .setTitle(item.title)
            .setStartTime(item.startTime)
            .setEndTime(item.endTime)
            .build()
    }

    override fun onEventClick(data: MyEvent){
        println("se dio click en el archivo: "+data.title);
        Toast.makeText(this.context,data.title, Toast.LENGTH_LONG).show()
    }

    override fun onEmptyViewClick(time: Calendar){
        var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        var datos4s : Date = formatter.parse("2022-11-25T09:00");
        var datos4f : Date = formatter.parse("2022-11-25T10:00");
        val newEvent = MyEvent(6,"Mi reserva",toCalendar(datos4s),toCalendar(datos4f))
        modelWeek.modifyEvents(MyEvent.events,newEvent);
        Toast.makeText(this.context,"new Event: "+newEvent.title, Toast.LENGTH_LONG).show()
    }

    fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        println("formato: "+cal.time);
        return cal
    }

    fun recorrerArray(mutableList: MutableList<MyEvent>){
        for (item in mutableList) {
            println(item.title)
        }
    }

}