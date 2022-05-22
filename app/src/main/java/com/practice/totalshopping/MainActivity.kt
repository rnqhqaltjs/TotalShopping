package com.practice.totalshopping

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.totalshopping.databinding.ActivityMainBinding
import com.practice.totalshopping.model.Items
import com.practice.totalshopping.model.ResultGetSearchShopping
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.GridLayoutManager
import com.example.totalshopping.R
import com.practice.totalshopping.info.InfoActivity
import com.practice.totalshopping.model.NaverAPI
import com.practice.totalshopping.sqlite.BookmarkActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val CLIENT_ID = "QDFQdRtj2gNP9KSLncJP"
    private val CLIENT_SECRET = "GgXI99w7Ms"
    private var display : Int = 10
    private var start : Int = 1
    private var sort : String = "sim"
    private var searchword : String = ""
    private var FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0
    lateinit var toggle:ActionBarDrawerToggle

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(NaverAPI::class.java)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(this,binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        supportActionBar?.title = "토탈 쇼핑"

        //인터넷 연결 안될시 종료
        if(!isNetworkAvailable(this)){
            val toast = Toast.makeText(this, "인터넷 연결을 확인해주세요.  \r\n어플리케이션을 종료합니다.", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)
            toast.show()

            ActivityCompat.finishAffinity(this)
            exitProcess(0)
        }

        //스와이프 하면 새로고침
        binding.swipe.setOnRefreshListener {
            ShoppingResult(searchword)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.swipe.isRefreshing = false
        }

        binding.spinner.adapter = ArrayAdapter.createFromResource(this, R.array.itemList,android.R.layout.simple_spinner_item)
        binding.spinner2.adapter = ArrayAdapter.createFromResource(this, R.array.itemList2,android.R.layout.simple_spinner_item)
        binding.spinner3.adapter = ArrayAdapter.createFromResource(this, R.array.itemList3,android.R.layout.simple_spinner_item)


        //정렬 방식 1
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {

                    //20개
                    0 -> {
                        display = 20
                        ShoppingResult(searchword)
                        if (view != null) {
                            Snackbar.make(view, "20개", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    //50개
                    1 -> {
                        display = 50
                        ShoppingResult(searchword)
                        if (view != null) {
                            Snackbar.make(view, "50개", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    //100개
                    2 -> {
                        display = 100
                        ShoppingResult(searchword)
                        if (view != null) {
                            Snackbar.make(view, "100개", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    //일치하는게 없는 경우
                    else -> {

                    }
                }
            }
        }

        //정렬 방식 2
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    //유사도순
                    0 -> {
                        sort = "sim"
                        ShoppingResult(searchword)
                        if (view != null) {
                            Snackbar.make(view, "인기상품순", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    //등록일순
                    1 -> {
                        sort = "date"
                        ShoppingResult(searchword)
                        if (view != null) {
                            Snackbar.make(view, "신상품순", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    //최저가순
                    2 -> {
                        sort = "asc"
                        ShoppingResult(searchword)
                        if (view != null) {
                            Snackbar.make(view, "최저가순", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    //최고가순
                    3 -> {
                        sort = "dsc"
                        ShoppingResult(searchword)
                        if (view != null) {
                            Snackbar.make(view, "최고가순", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    //일치하는게 없는 경우
                    else -> {

                    }
                }
            }
        }


        //정렬 방식 3
        binding.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    //세로
                    0 -> {
                        binding.recyclerView.isVisible = true
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                        binding.recyclerView2.isVisible = false
                        if (view != null) {
                            Snackbar.make(view, "세로정렬", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    //액자
                    1 -> {
                        binding.recyclerView2.isVisible = true
                        binding.recyclerView2.layoutManager = GridLayoutManager(this@MainActivity,2,  GridLayoutManager.VERTICAL, false)
                        binding.recyclerView.isVisible = false
                        if (view != null) {
                            Snackbar.make(view, "액자정렬", Snackbar.LENGTH_SHORT).show()
                        }


                    }

                    //일치하는게 없는 경우
                    else -> {

                    }
                }
            }
        }


        //검색 버튼 눌렀을 때 이벤트
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {


            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {



                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                ShoppingResult(query)

                if (query != null) {
                    searchword = query
                }

                binding.textView.isVisible = false

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // 검색창에서 글자가 변경이 일어날 때마다 호출

                return true
            }
        })

        //툴바
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.item_bookmark -> {

                    val intent = Intent(this, BookmarkActivity::class.java)
                    startActivity(intent)

                }

                R.id.item_out -> {

                    moveTaskToBack(true)
                    finishAndRemoveTask()
                    exitProcess(0)

                }

                R.id.item_info -> {

                    val intent = Intent(this, InfoActivity::class.java)
                    startActivity(intent)

                }

            }
            true
        }


    }

    //툴바
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //검색 결과를 가져옴
    private fun ShoppingResult(query: String?) {

        query?.let {
            api.getSearchShopping(CLIENT_ID, CLIENT_SECRET, it,display,sort,start)
        }?.enqueue(object : Callback<ResultGetSearchShopping> {
            override fun onResponse(
                call: Call<ResultGetSearchShopping>,
                response: Response<ResultGetSearchShopping>
            ) {

                binding.recyclerView.adapter = response.body()
                    ?.let { RecyclerViewAdapter(this@MainActivity, it.items as MutableList<Items>) }
                binding.recyclerView.setHasFixedSize(true)

                binding.recyclerView2.adapter = response.body()
                    ?.let { RecyclerViewAdapter2(this@MainActivity, it.items as MutableList<Items>) }
                binding.recyclerView2.setHasFixedSize(true)


                Log.d(TAG, "성공 : ${response.body()}")

            }

            override fun onFailure(call: Call<ResultGetSearchShopping>, t: Throwable) {

                Log.d(TAG, "실패 : $t")

            }
        })
    }

    //뒤로 버튼 2번 누르면 종료
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0) {
            val tempTime = System.currentTimeMillis();
            val intervalTime = tempTime - backPressedTime;
            if (intervalTime in 0..FINISH_INTERVAL_TIME) {
                finish()
            } else {
                backPressedTime = tempTime;
                Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                return
            }
        }
        super.onBackPressed();
    }


    //인터넷 연결 여부 확인
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }


}