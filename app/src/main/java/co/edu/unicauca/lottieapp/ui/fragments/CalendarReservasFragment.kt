package co.edu.unicauca.lottieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import co.edu.unicauca.lottieapp.ui.adapters.CalendarAdapter
import co.edu.unicauca.calendarweekview.weekViewModel
import co.edu.unicauca.lottieapp.databinding.FragmentCalendarReservasBinding
import com.alamkanak.weekview.WeekView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.data.model.MyEvent
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.data.model.eventosResponse
import co.edu.unicauca.lottieapp.service.APIService
import co.edu.unicauca.lottieapp.utils.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

/*
Creado por: Kevith Felipe Bastidas, Jefferson Eduardo Campo, Danny Alberto Díaz Mage, Juliana Mora
 */

/**
 * Clase de tipo Fragmento en el que se muestran las reservas de cada escenario deportivo
 */
class CalendarReservasFragment : Fragment() {
    //Atributos
    private var _binding: FragmentCalendarReservasBinding? = null

    private val binding: FragmentCalendarReservasBinding get() = _binding!!

    private val eventos = mutableListOf<eventosResponse>()

    private val adapt : CalendarAdapter = CalendarAdapter(this)

    private val viewModel by viewModels<weekViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCalendarReservasBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val weekView: WeekView = binding.weekView
        weekView.adapter = adapt
        setFragmentResultListener("keyEsc"){ requestKey, bundle ->
            val auxresult = bundle.getString("idescenariores")!!
            adapt.setAuxVar(auxresult)
            if(eventos.isEmpty()) {
                buscarEventos(auxresult)
            }
        }

        setFragmentResultListener("idescenarioqr"){requestKey, bundle ->
            val auxId = bundle.getString("idescenario")!!
            if(eventos.isEmpty()) {
                buscarEventos(auxId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null){
            findNavController().navigate(R.id.locationFragment)
        }
    }

    /**
     *  Función para conectarse a la API
     *  @return la instancia de tipo retrofit para conectarse a la API
     */
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }


    /**
     * Función que me permite obtener los eventos de la API
     * @param query = parametro que tiene el id del escenario
     */
    private fun buscarEventos(query: String) {
        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
            val call = getRetrofit().create(APIService::class.java).getHorariosByEscenario("horarios/escenario/${query}")
            activity?.runOnUiThread() {
                val listaEventos: List<eventosResponse> = call.body() ?: emptyList()
                if (call.isSuccessful) {
                    //Show reclerview
                    eventos.clear()
                    eventos.addAll(listaEventos)
                    viewModel.eventos.value = convertToEvent(eventos)
                    viewModel.eventos.observe(this@CalendarReservasFragment) {events ->
                        adapt.submitList(events)
                    }
                } else {
                    //Show Error
                    showError()
                }
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        activity?.runOnUiThread {
            Toast.makeText(activity,"Servidor no encontrado: " + exception,Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Función que me permite mostrar al usuario si ha ocurrido un error al obtener algun dato de la API
     */
    private fun showError() {
        Toast.makeText(activity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    /**
     * Función que me permite convertir los eventos que llegan de la base de datos a objetos de tipo MyEvent con formato Calendar
     * @param eventos = lista de eventosresponse obtenidos de la base de datos
     * @return lista de eventos de tipo MyEvent
     */
    private fun convertToEvent(eventos: MutableList<eventosResponse>):MutableList<MyEvent>{
        var contador: Long = 1;
        var listaEventos: MutableList<MyEvent> = mutableListOf()
        for (evento in eventos){
            var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
            var cadenaInicio : Date = formatter.parse(evento.pk_horario.hor_fecha_inicio + "T" + evento.pk_horario.hor_hora_inicio + ":00")
            var cadenaFin : Date = formatter.parse(evento.pk_horario.hor_fecha_fin + "T" + evento.pk_horario.hor_hora_fin + ":00")
            val newEvent = MyEvent(contador,"Evento "+contador,toCalendar(cadenaInicio),toCalendar(cadenaFin))
            listaEventos.add(newEvent)
            contador++
        }
        return listaEventos
    }

    /**
     * Función que me permite convertir un tipo de dato Date a Calendar
     * @param date = parámetro de tipo dato
     * @return tipo de dato calendario
     */
    fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }
}