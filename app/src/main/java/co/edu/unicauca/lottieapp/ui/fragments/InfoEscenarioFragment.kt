package co.edu.unicauca.lottieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.unicauca.lottieapp.databinding.FragmentInfoEscenarioBinding
import co.edu.unicauca.lottieapp.data.model.escenarioResponse
import co.edu.unicauca.lottieapp.service.Imagenes

/**
 * A simple [Fragment] subclass.
 * Use the [InfoEscenarioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoEscenarioFragment : Fragment() {

    lateinit var escenario: escenarioResponse
    private var _binding: FragmentInfoEscenarioBinding? = null

    private val binding: FragmentInfoEscenarioBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoEscenarioBinding.inflate(inflater,container,false)
        escenario = arguments?.getSerializable("escenario") as escenarioResponse
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        showInfoEscenario()
    }

    private fun showInfoEscenario() {
        binding.infoEscenarioName.text = escenario.esc_nombre
        binding.infoEscenarioPhoto.setImageResource(Imagenes.images[escenario.esc_nombre].hashCode())
        binding.infoEscenarioDescription.text = escenario.esc_descripcion

        /*
        val imageId: ImageView = binding.root.findViewById(R.id.info_escenario_photo)
        val description =  binding.root.findViewById<TextView>(R.id.info_escenario_description)
        val name =  binding.root.findViewById<TextView>(R.id.info_escenario_name)
        val imageBytes = Base64.decode(escenario.esc_foto, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
        imageId.setImageBitmap(decodedImage)
        name.text=escenario.esc_nombre
        description.text=escenario.esc_descripcion

         */
    }


}