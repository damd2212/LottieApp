package co.edu.unicauca.lottieapp.data.model

import com.google.gson.annotations.SerializedName

data class PK_horario (
    @SerializedName("hor_hora_inicio") var hor_hora_inicio:Int,
    @SerializedName("hor_hora_fin") var hor_hora_fin:Int,
    @SerializedName("hor_dia") var hor_dia:String,
    @SerializedName("hor_fecha_inicio") var hor_fecha_inicio:String,
    @SerializedName("hor_fecha_fin") var hor_fecha_fin:String,
    @SerializedName("esc_nombre") var esc_nombre:String
):java.io.Serializable