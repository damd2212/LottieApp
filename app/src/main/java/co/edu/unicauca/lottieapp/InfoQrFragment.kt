package co.edu.unicauca.lottieapp

import android.app.ActionBar
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.databinding.FragmentInfoQrBinding
import co.edu.unicauca.lottieapp.models.escenarioResponse
import co.edu.unicauca.lottieapp.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InfoQrFragment : Fragment() {

    private var _binding:FragmentInfoQrBinding? = null

    private val binding: FragmentInfoQrBinding get() = _binding!!

    var ayuda: String? = "-1"

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
        //bundle = savedInstanceState
        /*if (bundle.getBundle("id") != null){

        }*/
        println("---------------------")
        println("Instancia")
        println(savedInstanceState)
        println("---------------------")

        if (savedInstanceState != null){
            /*println("--------------------------")
            var nom_esc = savedInstanceState.getString("id")
            println("Valor encontrado $nom_esc")
            println("-----------------------------")
            searchByID(nom_esc)*/

            findNavController().navigate(R.id.action_infoQrFragment_to_qrFragment)
        }else{
            setFragmentResultListener("key"){ requestKey, bundle ->
                val result = bundle.getString("idescenario")
                searchByID(result)
            }
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
                    ayuda = escenario?.esc_nombre
                    //bundle?.putString("id",escenario?.esc_nombre)
                    imprimirDatos(escenario)


                }else{
                    Toast.makeText(activity,"Erro en el servidor",Toast.LENGTH_SHORT)
                }
            }

        }

    }

    private fun imprimirDatos( prmEscenario : escenarioResponse?){
        if (prmEscenario?.esc_nombre == "-1"){
            binding.titleInfoQr.text = "Escenario no encontrado"
            binding.imageInfoQr.setImageResource(R.drawable.nodisponible)
        }else{

            binding.titleInfoQr.text = prmEscenario?.esc_nombre
            val imageBytes = Base64.decode(prmEscenario?.esc_foto, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
            binding.imageInfoQr.setImageBitmap(decodedImage)
            binding.stateInfoQr.text = prmEscenario?.esc_estado
            binding.descriptionInfoQr.text = prmEscenario?.esc_descripcion
        }
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("id",ayuda)
        outState.putString("android:view_state","")
        super.onSaveInstanceState(outState)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {

        super.onViewStateRestored(savedInstanceState)
        val prueba : String? = savedInstanceState?.getString("id")

    }*/




}