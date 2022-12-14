package co.edu.unicauca.lottieapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import co.edu.unicauca.lottieapp.databinding.FragmentFormReservaBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FormReservaFragment : Fragment() {

    private var _binding: FragmentFormReservaBinding? = null

    private val binding: FragmentFormReservaBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFormReservaBinding.inflate(inflater,container,false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setFragmentResultListener("keyForm"){ requestKey, bundle ->
                val result = bundle.getString("idEscenarioForm")
                this.binding.idEvento.setText(result)
        }
        setFragmentResultListener("timeForm"){ requestKey, bundle ->
            val result: Calendar = bundle.get("time") as Calendar
            val list = result.time.toString().split(" ")
            val listHora = list.get(3).split(":")
            val horaFin = listHora.get(0).toInt()+1
            val fechaInicioFin: String = list.get(5)+"-"+toMonth(list.get(1))+"-"+list.get(2)
            this.binding.fechaInicio.setText(fechaInicioFin)
            this.binding.horaInicio.setText(listHora.get(0))
            this.binding.horaFin.setText(horaFin.toString())
            this.binding.dia.setText(toDay(list.get(0)))
        }

    }

    fun toMonth(month: String): String {
        var monthRes: String
        when (month) {
            "Jan" -> monthRes="1"
            "Feb" -> monthRes="2"
            "Mar" -> monthRes="3"
            "Apr" -> monthRes="4"
            "May" -> monthRes="5"
            "Jun" -> monthRes="6"
            "Jul" -> monthRes="7"
            "Aug" -> monthRes="8"
            "Sep" -> monthRes="9"
            "Oct" -> monthRes="10"
            "Nov" -> monthRes="11"
            "Dec" -> monthRes="12"
            else -> { // Note the block
                monthRes = "0"
            }
        }
        return monthRes
    }

    fun toDay(day: String): String{
        var dayRes : String
        when (day) {
            "Mon" -> dayRes="LUNES"
            "Tue" -> dayRes="MARTES"
            "Wed" -> dayRes="MIERCOLES"
            "Thu" -> dayRes="JUEVES"
            "Fri" -> dayRes="VIERNES"
            "Sat" -> dayRes="SABADO"
            "Sun" -> dayRes="DOMINGO"
            else -> { // Note the block
                dayRes = "NULL"
            }
        }
        return dayRes
    }

}