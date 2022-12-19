package co.edu.unicauca.lottieapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.ui.adapters.CategoriaAdapter
import co.edu.unicauca.lottieapp.databinding.FragmentLocationBinding
import co.edu.unicauca.lottieapp.data.model.categoriaResponse
import co.edu.unicauca.lottieapp.service.APIService
import co.edu.unicauca.lottieapp.utils.Constants
import com.google.android.material.internal.ViewUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LocationFragment : Fragment(),OnQueryTextListener, CategoriaAdapter.OnUserClickListener {

    private var _binding: FragmentLocationBinding? = null
    private val binding: FragmentLocationBinding get() = _binding!!
    private lateinit var adapter: CategoriaAdapter
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
        if(categorias.isEmpty())
            searchCategorias()
    }

    private fun initRecyclerView() {
        adapter = CategoriaAdapter(categorias, this)
        binding.rvCategoria.layoutManager = LinearLayoutManager(activity)
        binding.rvCategoria.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URL)
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

    private fun searchCategorias(){
        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {

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
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        activity?.runOnUiThread {
            Toast.makeText(activity,"Servidor no encontrado: " + exception,Toast.LENGTH_LONG).show()
        }
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

    override fun onImageClick(categoria: categoriaResponse) {
        var bundle = bundleOf("categoria" to categoria)
        findNavController().navigate(R.id.action_locationFragment_to_infoCategoriaFragment,bundle)
    }

    override fun onItemClick(categoria: categoriaResponse) {
        val bundle = bundleOf("categoria" to categoria)
        findNavController().navigate(R.id.action_locationFragment_to_escenarioFragment,bundle)
    }



}