package com.tagava


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: LinearLayoutManager
    private var adapter: Recycler_View_Adapter? = null
    lateinit var context:Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        val data: ArrayList<Data>? = fill_with_data()

        data?.let {
            adapter = Recycler_View_Adapter(data)
        }
        layoutManager = LinearLayoutManager(context)
        recyclerview?.adapter = adapter
        recyclerview?.layoutManager =layoutManager


    }

    //Create a list of Data objects
    fun fill_with_data(): ArrayList<Data>? {
        val data: ArrayList<Data> = ArrayList()
        data.add(
            Data(
                "Rajiv Sharma",
                "2 hours ago",
                "RS",
                "Rs 2000",
                "You'll get",
                true

            )
        )
        data.add(
            Data(
                "Ashish Sharma",
                "8 hours ago",
                "AS",
                "Rs 1550",
                "You'll get",
                true
            )
        )
        data.add(
                Data(
                    "Mehul Sharma",
                    "2 days ago",
                    "MS",
                    "Rs 2000",
                    "You'll give",
                    false
                )
        )

        data.add(
            Data(
                "Kirti Desai",
                "5 days ago",
                "KD",
                "Rs 6000",
                "You'll give",
                false

            )
        )
        data.add(
            Data(
                "Suraj Pathak",
                "8 days ago",
                "SP",
                "Rs 9000",
                "You'll get",
                true
            )
        )
        data.add(
            Data(
                "Lallan  Yadav",
                "12 days ago",
                "LY",
                "Rs 1000",
                "You'll give",
                false
            )
        )


        return data
    }
}
