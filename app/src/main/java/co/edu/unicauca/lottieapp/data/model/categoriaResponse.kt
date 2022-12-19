package co.edu.unicauca.lottieapp.data.model

import com.google.gson.annotations.SerializedName

data class categoriaResponse (
    @SerializedName("cat_nombre") var name:String,
    @SerializedName("cat_descripcion") var description:String,
    @SerializedName("cat_foto") var photo:String):java.io.Serializable