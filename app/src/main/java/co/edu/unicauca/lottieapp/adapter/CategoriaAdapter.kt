package co.edu.unicauca.lottieapp.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.ItemCategoriaBinding
import co.edu.unicauca.lottieapp.models.categoriaResponse

class CategoriaAdapter( var categorias:List<categoriaResponse>, val itemClickListener:OnUserClickListener):RecyclerView.Adapter<BaseViewHolder<*>>(){

    interface OnUserClickListener{
        fun onImageClick(categoria: categoriaResponse)
        fun onItemClick(categoria: categoriaResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        return CategoriaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_categoria,parent,false))

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is CategoriaViewHolder -> holder.bind(categorias[position],position)
            else -> throw java.lang.IllegalArgumentException("Se olvido de pasar el viewholder en el bind")
        }
    }

    override fun getItemCount(): Int = categorias.size


    inner class CategoriaViewHolder(val view: View): BaseViewHolder<categoriaResponse>(view) {

        private val binding = ItemCategoriaBinding.bind(view)

        override fun bind(categoria: categoriaResponse,position: Int){

            binding.name.text = categoria.name

            val imageBytes = Base64.decode(categoria.photo, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
            binding.photo.setImageBitmap(decodedImage)

            binding.description.text = categoria.description
            view.setOnClickListener{itemClickListener.onImageClick(categoria)}
            binding.btnAceptar.setOnClickListener{itemClickListener.onItemClick(categoria)}

        }
    }

}