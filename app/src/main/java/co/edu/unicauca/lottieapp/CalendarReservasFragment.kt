package co.edu.unicauca.lottieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.unicauca.lottieapp.databinding.FragmentCalendarReservasBinding
import co.edu.unicauca.lottieapp.databinding.FragmentEscenarioBinding

class CalendarReservasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentCalendarReservasBinding? = null
    private val binding: FragmentCalendarReservasBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_reservas, container, false)
    }
}