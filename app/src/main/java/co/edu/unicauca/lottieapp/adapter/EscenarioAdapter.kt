package co.edu.unicauca.lottieapp.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.ItemEscenarioBinding
import co.edu.unicauca.lottieapp.models.escenarioResponse

class EscenarioAdapter(var escenarios:List<escenarioResponse>, val itemClickListener: EscenarioAdapter.OnUserClickListener): RecyclerView.Adapter<BaseViewHolder<*>>(){

    interface OnUserClickListener{
        fun onImageClick(escenario: escenarioResponse)
        fun onItemClick(escenario: escenarioResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscenarioViewHolder {
        return EscenarioViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_escenario,parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is EscenarioAdapter.EscenarioViewHolder -> holder.bind(escenarios[position],position)
            else -> throw java.lang.IllegalArgumentException("Se olvido de pasar el viewholder en el bind")
        }
    }

    override fun getItemCount(): Int = escenarios.size

    inner class EscenarioViewHolder(val view: View): BaseViewHolder<escenarioResponse>(view) {

        private val binding = ItemEscenarioBinding.bind(view)

        override fun bind(escenario: escenarioResponse, position: Int){

            binding.escenarioName.text = escenario.esc_nombre


            val imageBytes = Base64.decode(escenario.esc_foto, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
            binding.escenarioPhoto.setImageBitmap(decodedImage)

            binding.escenarioDescription.text = escenario.esc_descripcion
            view.setOnClickListener{itemClickListener.onImageClick(escenario)}
            binding.btnEscenarioReservar.setOnClickListener{itemClickListener.onItemClick(escenario)}

        }
    }

}