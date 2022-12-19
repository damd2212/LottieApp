package co.edu.unicauca.lottieapp.data.model

import java.text.SimpleDateFormat
import java.util.*

/*
Creado por: Kevith Felipe Bastidas, Jefferson Eduardo Campo, Danny Alberto Díaz Mage, Juliana Mora
 */

data class MyEvent(
    val id: Long,
    val title: String,
    val startTime: Calendar,
    val endTime: Calendar,
){
    companion object{
        var events : MutableList<MyEvent> = mutableListOf()
            get() = mutableListOf()}
}
fun toCalendar(date: String): Calendar {
    var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    var datoss : Date = formatter.parse(date);
    val cal = Calendar.getInstance()
    cal.time = datoss
    return cal
}
