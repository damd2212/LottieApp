package co.edu.unicauca.lottieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.holder.EscenarioViewHolder
import co.edu.unicauca.lottieapp.models.escenarioResponse

class EscenarioAdapter(val escenarios:List<escenarioResponse>): RecyclerView.Adapter<EscenarioViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscenarioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EscenarioViewHolder(layoutInflater.inflate(R.layout.item_escenario,parent,false))
    }

    override fun onBindViewHolder(holder: EscenarioViewHolder, position: Int) {
        val item = escenarios[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = escenarios.size
}