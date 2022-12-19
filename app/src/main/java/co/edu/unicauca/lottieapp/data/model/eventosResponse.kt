package co.edu.unicauca.lottieapp.data.model

import com.google.gson.annotations.SerializedName

/*
Creado por: Kevith Felipe Bastidas, Jefferson Eduardo Campo, Danny Alberto Díaz Mage, Juliana Mora
 */

data class eventosResponse (
    @SerializedName("pk_horario") var pk_horario: PK_horario,
    @SerializedName("hor_estado") var hor_estado:Char,
    @SerializedName("pro_id") var pro_id:Int,
    ):java.io.Serializable