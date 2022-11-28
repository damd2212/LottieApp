package co.edu.unicauca.lottieapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.unicauca.lottieapp.adapter.EscenarioAdapter
import co.edu.unicauca.lottieapp.databinding.FragmentEscenarioBinding
import co.edu.unicauca.lottieapp.models.categoriaResponse
import co.edu.unicauca.lottieapp.models.escenarioResponse
import co.edu.unicauca.lottieapp.service.APIService
import com.google.android.material.internal.ViewUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EscenarioFragment : Fragment(), SearchView.OnQueryTextListener,EscenarioAdapter.OnUserClickListener {

    private var _binding: FragmentEscenarioBinding? = null
    private val binding: FragmentEscenarioBinding get() = _binding!!
    private lateinit var adapter: EscenarioAdapter
    private val escenarios = mutableListOf<escenarioResponse>()
    lateinit var categoria:categoriaResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEscenarioBinding.inflate(inflater,container,false)
        initRecyclerView()
        binding.svEscenario.setOnQueryTextListener(this)
        categoria = arguments?.getSerializable("categoria") as categoriaResponse
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        if(escenarios.isEmpty())
            searchEscenarios(categoria.name)


    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.128.3:8030/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun searchEscenarios(query: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(APIService::class.java).getEscenarios("categorias/escenarios/${query}")
            activity?.runOnUiThread() {
                val listaCategorias: List<escenarioResponse> = call.body() ?: emptyList()
                if (call.isSuccessful) {
                    //Show reclerview
                    escenarios.clear()
                    escenarios.addAll(listaCategorias)
                    adapter.notifyDataSetChanged()
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

    private fun initRecyclerView() {
        adapter = EscenarioAdapter(escenarios,this)
        binding.rvEscenario.layoutManager = LinearLayoutManager(activity)
        binding.rvEscenario.adapter = adapter
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
            adapter.escenarios = escenarios
            adapter.notifyDataSetChanged()
        }
        return true
    }

    private fun searchByID(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            activity?.runOnUiThread(){
                if(!escenarios.isEmpty()){
                    val listaEscenarios = escenarios.filter { it.esc_nombre.startsWith(query)}
                    adapter.escenarios = listaEscenarios
                    adapter.notifyDataSetChanged()
                }
            }

        }
    }

    override fun onImageClick(escenario: escenarioResponse) {
        var bundle = bundleOf("escenario" to escenario)
        findNavController().navigate(R.id.action_escenarioFragment_to_infoEscenarioFragment,bundle)
    }

    override fun onItemClick(escenario: escenarioResponse) {
        Toast.makeText(activity, "Reservación en construcción", Toast.LENGTH_SHORT).show()
    }
}