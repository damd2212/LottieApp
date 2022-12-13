package co.edu.unicauca.calendarweekview.adapter

import android.app.Activity
import android.app.RemoteInput
import android.content.Intent
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.calendarweekview.models.MyEvent
import co.edu.unicauca.calendarweekview.weekViewModel
import co.edu.unicauca.lottieapp.*
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import java.text.SimpleDateFormat
import co.edu.unicauca.lottieapp.repository.UserRepository
import java.util.*

class CalendarAdapter(par:CalendarReservasFragment) :WeekView.SimpleAdapter<MyEvent>() {

    private var calendar: CalendarReservasFragment = par
    private var modelWeek : weekViewModel = weekViewModel()
    private var auxVar: String = ""

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
            val intent = Intent(context,LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }else{
            var bundle = bundleOf("timeForm" to time)
            calendar.setFragmentResult("keyForm", bundleOf("idEscenarioForm" to this.auxVar))
            calendar.findNavController().navigate(R.id.action_calendarReservasFragment_to_formReservaFragment)
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

    fun setAuxVar(parAux: String){
        this.auxVar = parAux
    }

}