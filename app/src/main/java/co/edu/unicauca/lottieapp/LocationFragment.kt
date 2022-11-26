package co.edu.unicauca.lottieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.unicauca.lottieapp.adapter.EscenarioAdapter
import co.edu.unicauca.lottieapp.databinding.FragmentLocationBinding
import co.edu.unicauca.lottieapp.models.escenarioResponse
import co.edu.unicauca.lottieapp.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null

    private val binding: FragmentLocationBinding get() = _binding!!

    private lateinit var adapter:EscenarioAdapter
    private val escenarios = mutableListOf<escenarioResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater,container,false)
        initRecyclerView()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        searchEscenarios()

    }

    private fun initRecyclerView() {
        adapter = EscenarioAdapter(escenarios)
        binding.rvEscenario.layoutManager = LinearLayoutManager(activity)
        binding.rvEscenario.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8030/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun searchByID(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getEscenariosByID("escenarios/$query")
            //val escenarios = call.body()
            if (call.isSuccessful){
                println(call)
            }else{
                println("Error en la llamda de la api")
            }
        }
    }

    private fun searchEscenarios(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getEscenarios("escenarios")
            val escenario = call

            getActivity()?.runOnUiThread {
                if (!call.isEmpty()){
                    val listaEscenarios = escenario
                    escenarios.clear()
                    escenarios.addAll(listaEscenarios)
                    adapter.notifyDataSetChanged()
                    /*escenario.forEach{
                                println(it.esc_nombre)
                            }*/
                }else{
                    Toast.makeText(activity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}