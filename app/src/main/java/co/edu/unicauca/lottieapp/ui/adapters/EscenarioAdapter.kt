package co.edu.unicauca.lottieapp.ui.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.ItemEscenarioBinding
import co.edu.unicauca.lottieapp.data.model.escenarioResponse
import co.edu.unicauca.lottieapp.service.Imagenes

class EscenarioAdapter(var escenarios:List<escenarioResponse>, val itemClickListener: OnUserClickListener): RecyclerView.Adapter<BaseViewHolder<*>>(){

    interface OnUserClickListener{
        fun onImageClick(escenario: escenarioResponse)
        fun onItemClick(escenario: escenarioResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscenarioViewHolder {
        return EscenarioViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_escenario,parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is EscenarioViewHolder -> holder.bind(escenarios[position],position)
            else -> throw java.lang.IllegalArgumentException("Se olvido de pasar el viewholder en el bind")
        }
    }

    override fun getItemCount(): Int = escenarios.size

    inner class EscenarioViewHolder(val view: View): BaseViewHolder<escenarioResponse>(view) {

        private val binding = ItemEscenarioBinding.bind(view)

        override fun bind(escenario: escenarioResponse, position: Int){
            //val imageBytes = Base64.decode(escenario.esc_foto, Base64.DEFAULT)
            //val decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
            //binding.escenarioPhoto.setImageBitmap(decodedImage)

            val description = if (escenario.esc_descripcion.length >= 80) escenario.esc_descripcion.substring(0,80)+" <u><b>ver más...</b></u>" else escenario.esc_descripcion

            binding.escenarioName.text = escenario.esc_nombre
            binding.escenarioPhoto.setImageResource(Imagenes.images[escenario.esc_nombre].hashCode())
            binding.escenarioDescription.text = Html.fromHtml("${description}")

            view.setOnClickListener{itemClickListener.onImageClick(escenario)}
            binding.btnEscenarioReservar.setOnClickListener{itemClickListener.onItemClick(escenario)}

        }

        private fun description(description: String){
            description.substring(1,30)
        }
    }

}