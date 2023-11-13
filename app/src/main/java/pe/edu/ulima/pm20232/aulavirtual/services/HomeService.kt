package pe.edu.ulima.pm20232.aulavirtual.services

import pe.edu.ulima.pm20232.aulavirtual.models.Integrante
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("about") // Reemplaza con la URL de tu punto final
    fun about(
    ): Call<List<Integrante>>
}