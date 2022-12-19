package co.edu.unicauca.lottieapp.ui.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.ItemCategoriaBinding
import co.edu.unicauca.lottieapp.data.model.categoriaResponse
import co.edu.unicauca.lottieapp.service.Imagenes


class CategoriaAdapter(var categorias:List<categoriaResponse>, val itemClickListener: OnUserClickListener):RecyclerView.Adapter<BaseViewHolder<*>>(){

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

        override fun bind(categoria: categoriaResponse, position: Int){
            //val imageBytes = Base64.decode(categoria.photo, Base64.DEFAULT)
            //val decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
            //binding.photo.setImageBitmap(decodedImage)

            val description = if (categoria.description.length >= 80) categoria.description.substring(0,80)+" <u><b>ver más...</b></u>" else categoria.description

            binding.description.text = Html.fromHtml("${description}")
            binding.name.text = categoria.name
            binding.photo.setImageResource(Imagenes.images[categoria.name].hashCode())


            view.setOnClickListener{itemClickListener.onImageClick(categoria)}
            binding.btnAceptar.setOnClickListener{itemClickListener.onItemClick(categoria)}

        }
    }

}