package co.edu.unicauca.lottieapp.service

import co.edu.unicauca.lottieapp.models.categoriaResponse
import co.edu.unicauca.lottieapp.models.escenarioResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun getEscenariosByID(@Url url:String): Response<escenarioResponse>

    @GET
    suspend fun getEscenarios(@Url url: String): Response<List<escenarioResponse>>

    @GET
    suspend fun getCategorias(@Url url: String): Response<List<categoriaResponse>>
}