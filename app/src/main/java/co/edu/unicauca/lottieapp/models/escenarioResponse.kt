package co.edu.unicauca.lottieapp.models

import com.google.gson.annotations.SerializedName

data class escenarioResponse(
    @SerializedName("esc_nombre") var esc_nombre:String,
    @SerializedName("esc_descripcion") var esc_descripcion:String,
    @SerializedName("esc_foto") var esc_foto:String,
    @SerializedName("esc_estado") var esc_estado:String)