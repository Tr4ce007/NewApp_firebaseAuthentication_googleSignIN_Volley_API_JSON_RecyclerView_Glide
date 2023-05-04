package com.example.newsapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class HomeActivity : AppCompatActivity() {

    private lateinit var mAdapter:NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recView:RecyclerView=findViewById(/* id = */ R.id.recView)
        recView.layoutManager=LinearLayoutManager(this)
        mAdapter = NewsAdapter()
        recView.adapter=mAdapter
        val url="https://newsapi.org/v2/top-headlines?country=us&apiKey=2d60492e3bb4434a93c631e40d89acba"
        fetchData(url)
        val searchBtn:EditText=findViewById(R.id.searchBtn)
        searchBtn.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //https://newsapi.org/v2/everything?q=apple&apiKey=2d60492e3bb4434a93c631e40d89acba
                var url="https://newsapi.org/v2/everything?q="+p0+"&apiKey=2d60492e3bb4434a93c631e40d89acba"
                fetchData(url)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }


    private fun fetchData(url:String){
        //Toast.makeText(this,"fetch data()",Toast.LENGTH_LONG).show()
        //val url="https://newsapi.org/v2/top-headlines?country=us&apiKey=2d60492e3bb4434a93c631e40d89acba"
        //val url="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        val jsonObjectRequest=  object:JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                Log.d("e",it.getString("status"))
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()

                for(i in 1 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("urlToImage"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("publishedAt"),
                        newsJsonObject.getJSONObject("source").getString("name")
                    )
                    newsArray.add(news)
                }
                mAdapter.updatedNews(newsArray)

            },
            {
                //Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
                Log.d("e","error volley")
            })
        // overriding function to add Headers.
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        Log.d("e","succes api")
    }
}