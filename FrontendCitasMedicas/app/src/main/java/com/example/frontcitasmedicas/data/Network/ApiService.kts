import android.telecom.Call
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("apicita/guardar")
    fun guardarCita(@Body cita: Cita): Call<Cita>

    @GET("apicita/listar")
    fun listarCitas(): Call<List<Cita>>

    @GET("apicita/buscar/{id}")
    fun buscarCita(@Path("id") id: Long): Call<Cita>

    @PUT("apicita/actualizar")
    fun actualizarCita(@Body cita: Cita): Call<Cita>

    @DELETE("apicita/eliminar/{id}")
    fun eliminarCita(@Path("id") id: Long): Call<Void>
}
