package co.edu.unicauca.calendarweekview.adapter

import android.content.Intent
import android.widget.Toast
import co.edu.unicauca.calendarweekview.models.MyEvent
import co.edu.unicauca.calendarweekview.weekViewModel
import co.edu.unicauca.lottieapp.HomeActivity
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import java.text.SimpleDateFormat
import co.edu.unicauca.lottieapp.repository.UserRepository
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
        var usuario = UserRepository()
        if (usuario.getCurrentUser() == null){
            Toast.makeText(this.context,"No existe una sesion inciada", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this.context,"Formualrio en creacion", Toast.LENGTH_LONG).show()
        }

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