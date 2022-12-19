package co.edu.unicauca.lottieapp.ui.adapters

import android.content.Intent
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.data.model.MyEvent
import co.edu.unicauca.lottieapp.*
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import co.edu.unicauca.lottieapp.data.repository.UserRepository
import co.edu.unicauca.lottieapp.ui.activities.LoginActivity
import co.edu.unicauca.lottieapp.ui.fragments.CalendarReservasFragment
import java.util.*

class CalendarAdapter(par: CalendarReservasFragment) :WeekView.SimpleAdapter<MyEvent>() {

    private var calendar: CalendarReservasFragment = par
    private var auxVar: String = ""
    //private val userRepository by lazy { UserRepository() }

    override fun onCreateEntity(item: MyEvent): WeekViewEntity {
        return WeekViewEntity.Event.Builder(item)
            .setId(item.id)
            .setTitle(item.title)
            .setStartTime(item.startTime)
            .setEndTime(item.endTime)
            .build()
    }

    override fun onEventClick(data: MyEvent){
        Toast.makeText(this.context,"El horario ya ha sido reservado", Toast.LENGTH_LONG).show()
    }

    override fun onEmptyViewClick(time: Calendar){
        var usuario = UserRepository()
        if (usuario.getUsuario() == null){
            val intent = Intent(context, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }else{
            calendar.setFragmentResult("keyForm", bundleOf("idEscenarioForm" to this.auxVar))
            calendar.setFragmentResult("timeForm", bundleOf("time" to time))
            calendar.findNavController().navigate(R.id.action_calendarReservasFragment_to_formReservaFragment)
        }

    }

    fun setAuxVar(parAux: String){
        this.auxVar = parAux
    }

}