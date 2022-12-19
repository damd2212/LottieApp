package co.edu.unicauca.lottieapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.FragmentFormReservaBinding
import co.edu.unicauca.lottieapp.data.model.PK_horario
import co.edu.unicauca.lottieapp.data.model.eventosResponse
import co.edu.unicauca.lottieapp.service.APIService
import co.edu.unicauca.lottieapp.service.Imagenes
import co.edu.unicauca.lottieapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
                this.binding.escNombre.setImageResource(Imagenes.images[result].hashCode())
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

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    override fun onStart() {
        super.onStart()
        binding.fragmentReservaButton.setOnClickListener {
            var horario = PK_horario(binding.horaInicio.text.toString().toInt(),binding.horaFin.text.toString().toInt(),binding.dia.text.toString(),binding.fechaInicio.text.toString(),binding.fechaInicio.text.toString(),binding.idEvento.text.toString())
            var evento = eventosResponse(horario,'0',100)
            setFragmentResult("keyEsc", bundleOf("idescenariores" to horario.esc_nombre))
            guardarEvento(evento)
        }
    }

    fun guardarEvento(parEvento: eventosResponse){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).create(parEvento)
            withContext(Dispatchers.Main){
                if(call.body() != null){
                    Toast.makeText(activity,"Reserva guardada con exito ", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_formReservaFragment_to_calendarReservasFragment)
                } else {
                    Toast.makeText(activity,"Error al reservar el evento ", Toast.LENGTH_SHORT).show()
                }
            }
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