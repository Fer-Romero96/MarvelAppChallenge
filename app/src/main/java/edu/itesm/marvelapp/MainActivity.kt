package edu.itesm.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://gateway.marvel.com/v1/public/"
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var  results: Results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LinearLayoutManager(this)
        getAllData()
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAllData(){

        val callToService = getRetrofit().create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val resposeFromService = callToService.getComics()
            runOnUiThread {
                results = resposeFromService.body() as Results

                if(resposeFromService.isSuccessful){
                    Log.i("Comics", results.data?.results.toString())

                    recyclerView = findViewById<RecyclerView>(R.id.recyclerComics).apply {

                        layoutManager = manager
                        myAdapter = ComicsAdapter(results.data?.results)
                        adapter = myAdapter

                    }
                } else {
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_LONG).show()
                    Log.i("Error","Error")
                }
            }
        }

    }
}