import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.consultoriomedicoapp.R
import com.example.consultoriomedicoapp.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitasActivity : AppCompatActivity() {

    private lateinit var rvCitas: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        rvCitas = findViewById(R.id.rvCitas)
        rvCitas.layoutManager = LinearLayoutManager(this)

        listarCitas()
    }

    private fun listarCitas() {
        RetrofitClient.apiService.listarCitas().enqueue(object : Callback<List<Cita>> {
            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                if (response.isSuccessful) {
                    val citas = response.body()
                    // TODO: Asignar datos al adapter del RecyclerView
                }
            }

            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                // TODO: Manejar error
            }
        })
    }
}
