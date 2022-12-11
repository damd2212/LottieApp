package co.edu.unicauca.lottieapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.setFragmentResultListener
import co.edu.unicauca.calendarweekview.adapter.CalendarAdapter
import co.edu.unicauca.calendarweekview.weekViewModel
import co.edu.unicauca.lottieapp.databinding.FragmentCalendarReservasBinding
import com.alamkanak.weekview.WeekView
import java.time.format.DateTimeFormatter
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.calendarweekview.models.MyEvent
import co.edu.unicauca.calendarweekview.models.toCalendar
import co.edu.unicauca.lottieapp.adapter.EscenarioAdapter
import co.edu.unicauca.lottieapp.models.escenarioResponse
import co.edu.unicauca.lottieapp.models.eventosResponse
import co.edu.unicauca.lottieapp.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CalendarReservasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentCalendarReservasBinding? = null

    private val binding: FragmentCalendarReservasBinding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    private val eventos = mutableListOf<eventosResponse>()

    private lateinit var adapter: CalendarAdapter

    private val adapt : CalendarAdapter = CalendarAdapter();

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
            if(eventos.isEmpty()) {
                buscarEventos(auxresult)
            }
        }


    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }


    private fun buscarEventos(query: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(APIService::class.java).getHorariosByEscenario("horarios/escenario/${query}")
            activity?.runOnUiThread() {
                val listaEventos: List<eventosResponse> = call.body() ?: emptyList()
                if (call.isSuccessful) {
                    //Show reclerview
                    eventos.clear()
                    eventos.addAll(listaEventos)
                    for (evento in listaEventos){
                        println("eventos :"+evento.pk_horario)
                    }
                    var listaEventos : List<MyEvent> = listOf(MyEvent(
                        1,
                        "evento modificado",
                        toCalendar("2022-12-12T09:00"),
                        toCalendar("2022-12-12T10:00")
                    ));
                    var eventodds: MutableLiveData<List<MyEvent>>? = MutableLiveData()
                    eventodds?.value = listaEventos
                    if (eventodds != null) {
                        viewModel.eventos = eventodds
                    }
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

    private fun showError() {
        Toast.makeText(activity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}