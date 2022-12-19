package co.edu.unicauca.lottieapp.service

import co.edu.unicauca.lottieapp.data.model.categoriaResponse
import co.edu.unicauca.lottieapp.data.model.escenarioResponse
import co.edu.unicauca.lottieapp.data.model.eventosResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun getEscenariosByID(@Url url:String): Response<escenarioResponse>

    @GET
    suspend fun getEscenarios(@Url url: String): Response<List<escenarioResponse>>

    @GET
    suspend fun getCategorias(@Url url: String): Response<List<categoriaResponse>>

    @GET
    suspend fun getHorariosByEscenario(@Url url:String): Response<List<eventosResponse>>

    @POST("horarios")
    suspend fun create(@Body horario: eventosResponse): Response<eventosResponse>
}