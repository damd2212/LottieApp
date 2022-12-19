package co.edu.unicauca.lottieapp.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.ActivityCalendarReservasBinding
import com.alamkanak.weekview.WeekView
import java.time.format.DateTimeFormatter

class CalendarReservasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarReservasBinding

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarReservasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weekView: WeekView = binding.weekView
        

    }

    override fun onStart() {
        super.onStart()
        binding.buttomBackCalendar.setOnClickListener{
            val navController: NavController = Navigation.findNavController(this, R.id.home_nav)
            navController.navigateUp()
            navController.navigate(R.id.escenarioFragment)
        }
    }

}