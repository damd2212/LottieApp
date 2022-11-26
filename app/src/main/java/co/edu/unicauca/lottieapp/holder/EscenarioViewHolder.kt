package co.edu.unicauca.lottieapp.holder

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.edu.unicauca.lottieapp.databinding.ItemEscenarioBinding
import co.edu.unicauca.lottieapp.models.escenarioResponse

class EscenarioViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemEscenarioBinding.bind(view)

    fun bind(escenario: escenarioResponse){
        binding.tituloEsscenario.text = escenario.esc_nombre

        val imageBytes = Base64.decode(escenario.esc_foto, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
        binding.imageInfo.setImageBitmap(decodedImage)

        binding.descripcion.text = escenario.esc_descripcion
    }
}