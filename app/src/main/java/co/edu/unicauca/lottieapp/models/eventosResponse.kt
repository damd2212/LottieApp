package co.edu.unicauca.lottieapp.models

import com.google.gson.annotations.SerializedName

data class eventosResponse (
    @SerializedName("pk_horario") var pk_horario:PK_horario,
    @SerializedName("hor_estado") var hor_estado:Char,
    @SerializedName("pro_id") var pro_id:Int,
    @SerializedName("user_id") var user_id:String
    ):java.io.Serializable