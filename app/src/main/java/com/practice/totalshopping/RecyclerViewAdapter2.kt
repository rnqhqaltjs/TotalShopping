package com.practice.totalshopping


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.totalshopping.databinding.ItemMain2Binding
import com.example.totalshopping.databinding.ItemMainBinding
import com.practice.totalshopping.model.Items
import com.practice.totalshopping.model.ResultGetSearchShopping
import com.practice.totalshopping.sqlite.Memo
import com.practice.totalshopping.sqlite.SqliteHelper
import java.security.AccessController.getContext
import java.text.DecimalFormat
import java.util.logging.Filter

class RecyclerViewAdapter2(val context : Context, val items:MutableList<Items>)
    : RecyclerView.Adapter<RecyclerViewAdapter2.Viewholder>(){

    val helper = SqliteHelper(context,"memo",null,1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ItemMain2Binding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(private val binding: ItemMain2Binding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindItems(item: Items){

            val title = items[position].title
            val dec = DecimalFormat("###,###")
            val price = dec.format(items[position].lprice)
            val mallname =items[position].mallName

            binding.itemTitle.text = Html.fromHtml(title).toString()
            binding.itemPrice.text = "최저가" + " "+ price +"원"
            binding.itemMallname.text = mallname

            if(binding.itemTitle.text.length<=20){
                binding.itemTitle.text = binding.itemTitle.text as String + "                                             "
            }

            Glide.with(context)
                .load(item.image)
                .into(binding.itemImage)

            itemView.setOnClickListener {
                val webpage = Uri.parse(item.link.replace("search","msearch").replace("gate.nhn?id=","product/"))
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                context.startActivity(webIntent)

            }

            itemView.setOnLongClickListener {

                val builder = AlertDialog.Builder(context)
                builder.setTitle("알림")
                builder.setMessage("관심 항목에 등록하시겠습니까?")

                builder.setNegativeButton("아니오") { dialog, which ->

                }

                builder.setPositiveButton("네") { dialog, which ->

                    val memo = Memo(items[position].title, items[position].link,item.image,
                        items[position].lprice,items[position].mallName,items[position].category1,
                        items[position].category2,items[position].category3)
                    helper.insertMemo(memo)

                    Toast.makeText(context,"등록 완료", Toast.LENGTH_SHORT).show()

                }

                builder.show()


                return@setOnLongClickListener(true)
            }


        }

    }
}