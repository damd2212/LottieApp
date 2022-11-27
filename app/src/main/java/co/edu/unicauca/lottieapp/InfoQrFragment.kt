package co.edu.unicauca.lottieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import co.edu.unicauca.lottieapp.databinding.FragmentInfoQrBinding
import co.edu.unicauca.lottieapp.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InfoQrFragment : Fragment() {

    private var _binding:FragmentInfoQrBinding? = null

    private val binding: FragmentInfoQrBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoQrBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("key"){ requestKey, bundle ->
            val result = bundle.getString("idescenario")
            binding.titleInfoQr.text = result
            searchByID(result)

        }
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl("http://192.168.128.3:8030/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun searchByID(query:String?){

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getEscenariosByID("escenarios/$query")
            val escenario = call.body()
            getActivity()?.runOnUiThread {
                if (call.isSuccessful){
                    println(escenario?.esc_descripcion)
                    binding.descriptionInfoQr.text = escenario?.esc_descripcion
                }else{
                    println("Error en la llamda de la api")

                }
            }

        }

    }



}