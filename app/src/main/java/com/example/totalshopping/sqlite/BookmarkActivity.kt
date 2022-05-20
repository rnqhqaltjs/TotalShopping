package com.example.totalshopping.sqlite

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.totalshopping.R
import com.example.totalshopping.databinding.ActivityBookmarkBinding
import com.example.totalshopping.databinding.ActivityMainBinding

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    private val listData = ArrayList<Memo>()
    private val helper = SqliteHelper(this,"memo",null,1)
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "관심 항목"

        val adapter = BookmarkRVAdapter(this,listData,helper)
        adapter.listData.addAll(helper.selectMemo())
        adapter.helper = helper

        binding.recyclerView.adapter = adapter

        val manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        manager.reverseLayout =true
        manager.stackFromEnd = true

        binding.recyclerView.layoutManager = manager

        adapter.listData.clear()
        adapter.listData.addAll(helper.selectMemo())
        adapter.notifyDataSetChanged()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bookmark_menu, menu)
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.deleteall -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림")
                builder.setMessage("전체 항목을 삭제하시겠습니까?")

                builder.setNegativeButton("아니오") { dialog, which ->

                }

                builder.setPositiveButton("네") { dialog, which ->

                    helper.deleteAllMemo()
                    listData.clear()

                    Toast.makeText(this,"전체 삭제 완료",Toast.LENGTH_SHORT).show()

                    binding.recyclerView.adapter?.notifyDataSetChanged()

                }

                builder.show()
                return super.onOptionsItemSelected(item)
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}