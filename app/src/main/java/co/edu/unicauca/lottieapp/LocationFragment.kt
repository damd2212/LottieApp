package co.edu.unicauca.lottieapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.unicauca.lottieapp.adapter.CategoriaAdapter
import co.edu.unicauca.lottieapp.databinding.FragmentLocationBinding
import co.edu.unicauca.lottieapp.models.categoriaResponse
import co.edu.unicauca.lottieapp.models.escenarioResponse
import co.edu.unicauca.lottieapp.service.APIService
import com.google.android.material.internal.ViewUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.Collator


class LocationFragment : Fragment(),OnQueryTextListener {

    private var _binding: FragmentLocationBinding? = null

    private val binding: FragmentLocationBinding get() = _binding!!

    private lateinit var adapter:CategoriaAdapter
    private val escenarios = mutableListOf<escenarioResponse>()

    private val categorias = mutableListOf<categoriaResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater,container,false)
        initRecyclerView()
        binding.svCategoria.setOnQueryTextListener(this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //searchEscenarios()
        if(categorias.isEmpty())
            searchCategorias()


    }

    private fun initRecyclerView() {
        adapter = CategoriaAdapter(categorias)
        binding.rvCategoria.layoutManager = LinearLayoutManager(activity)
        binding.rvCategoria.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.21:8030/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun searchByID(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            activity?.runOnUiThread(){
                if(!categorias.isEmpty()){
                    val listaCategoria = categorias.filter { it.name.startsWith(query)}
                    adapter.categorias = listaCategoria
                    adapter.notifyDataSetChanged()
                }
            }

        }
    }

    private fun hasWord(word1:String,word2:String):Int{
        val comparador = Collator.getInstance()
        comparador.strength = Collator.PRIMARY


        for(c in word1){
            for(d in word2){
                if(comparador.compare(c,d)==0){

                }
            }
        }


        return 1
    }

    private fun searchEscenarios(){
        /*
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

         */
    }

    private fun searchCategorias(){
        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(APIService::class.java).getCategorias("categorias")
            activity?.runOnUiThread(){
                val listaCategorias: List<categoriaResponse> = call.body()?: emptyList()
                if(call.isSuccessful){
                    //Show reclerview
                    categorias.clear()
                    categorias.addAll(listaCategorias)
                    adapter.notifyDataSetChanged()
                }else{
                    //Show Error
                    showError()
                }
            }


            /*

            getActivity()?.runOnUiThread {
                if (!call.isEmpty()){
                    val listaCategorias = call

                    categorias.clear()
                    categorias.addAll(listaCategorias)
                    adapter.notifyDataSetChanged()
                    /*escenario.forEach{
                                println(it.esc_nombre)
                            }*/
                }else{
                    Toast.makeText(activity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
            }

             */
        }
/*
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getEscenarios("escenarios")
            activity?.runOnUiThread(){
                val listaCategorias: List<escenarioResponse> = call.body()?: emptyList()
                if(call.isSuccessful){
                    //Show reclerview

                    categorias.clear()
                    categorias.addAll(listaCategorias)
                    adapter.notifyDataSetChanged()
                }else{
                    //Show Error
                    showError()
                }
            }
        }

 */
    }

    private fun showError() {
        Toast.makeText(activity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("RestrictedApi")
    override fun onQueryTextSubmit(query: String?): Boolean {
        ViewUtils.hideKeyboard(binding.root)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(!newText.isNullOrEmpty() || !newText.isNullOrBlank()){
            searchByID(newText.toUpperCase())
        }else{
            adapter.categorias = categorias
            adapter.notifyDataSetChanged()
        }
        return true
    }

}