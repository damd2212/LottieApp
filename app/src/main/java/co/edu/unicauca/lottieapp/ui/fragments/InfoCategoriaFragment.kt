package co.edu.unicauca.lottieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.unicauca.lottieapp.databinding.FragmentInfoCategoriaBinding
import co.edu.unicauca.lottieapp.data.model.categoriaResponse
import co.edu.unicauca.lottieapp.service.Imagenes


/**
 * A simple [Fragment] subclass.
 * Use the [InfoCategoriaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoCategoriaFragment : Fragment() {

    lateinit var categoria: categoriaResponse
    private var _binding: FragmentInfoCategoriaBinding? = null

    private val binding: FragmentInfoCategoriaBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoCategoriaBinding.inflate(inflater,container,false)

        categoria = arguments?.getSerializable("categoria") as categoriaResponse
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        showInfoLocation()
    }

    private fun showInfoLocation(){
        binding.infoLocationName.text = categoria.name
        binding.infoLocationDescription.text = categoria.description
        binding.infoLocationPhoto.setImageResource(Imagenes.images[categoria.name].hashCode())
        /*
        val imageId: ImageView = binding.root.findViewById(R.id.info_location_photo)
        val description =  binding.root.findViewById<TextView>(R.id.info_location_description)
        val name =  binding.root.findViewById<TextView>(R.id.info_location_name)
        val imageBytes = Base64.decode(categoria.photo, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
        imageId.setImageBitmap(decodedImage)
        name.text=categoria.name
        description.text=categoria.description

         */
    }

}