package co.edu.unicauca.lottieapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.FragmentInfoQrBinding
import co.edu.unicauca.lottieapp.data.model.escenarioResponse
import co.edu.unicauca.lottieapp.service.APIService
import co.edu.unicauca.lottieapp.service.Imagenes
import co.edu.unicauca.lottieapp.utils.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InfoQrFragment : Fragment() {

    private var _binding:FragmentInfoQrBinding? = null

    private val binding: FragmentInfoQrBinding get() = _binding!!

    var ayuda: String? = ""

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

        if (savedInstanceState != null){
            findNavController().navigate(R.id.action_infoQrFragment_to_qrFragment)
        }else{
            setFragmentResultListener("key"){ requestKey, bundle ->
                val result = bundle.getString("idescenario")
                searchByID(result)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.buttonReservaInfoQr.isVisible = false
        binding.buttonReservaInfoQr.setOnClickListener {
            setFragmentResult("idescenarioqr", bundleOf("idescenario" to ayuda))
            findNavController().navigate(R.id.action_infoQrFragment_to_calendarReservasFragment)
            Toast.makeText(activity,ayuda,Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun searchByID(query:String?){

        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
            val call = getRetrofit().create(APIService::class.java).getEscenariosByID("escenarios/$query")
            val escenario = call.body()
            getActivity()?.runOnUiThread {
                if (call.isSuccessful){
                    println(escenario?.esc_descripcion)
                    ayuda = escenario?.esc_nombre
                    imprimirDatos(escenario)
                }else{
                    Toast.makeText(activity,"Erro en el servidor",Toast.LENGTH_SHORT)
                }
            }

        }

    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        activity?.runOnUiThread {
            Toast.makeText(activity,"Servidor no encontrado: " + exception,Toast.LENGTH_LONG).show()
        }
    }

    private fun imprimirDatos( prmEscenario : escenarioResponse?){
        if (prmEscenario?.esc_nombre == "-1"){
            binding.titleInfoQr.text = "Escenario no encontrado"
            binding.imageInfoQr.setImageResource(R.drawable.nodisponible)

        }else{

            binding.imageInfoQr.setImageResource(Imagenes.images[prmEscenario?.esc_nombre].hashCode())
            binding.titleInfoQr.text = prmEscenario?.esc_nombre
            var estado = "ACTIVO"
            if (prmEscenario?.esc_estado == "0"){
                estado = "INACTIVO"
                binding.stateInfoQr.setTextColor(Color.RED)
            }else{
                binding.buttonReservaInfoQr.isVisible = true
                binding.stateInfoQr.setTextColor(Color.GREEN)
            }
            binding.stateInfoQr.text = estado
            binding.descriptionInfoQr.text = prmEscenario?.esc_descripcion
        }
    }

}