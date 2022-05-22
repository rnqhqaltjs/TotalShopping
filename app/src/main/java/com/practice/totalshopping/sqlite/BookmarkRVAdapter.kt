package com.practice.totalshopping.sqlite

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.totalshopping.databinding.BookmarkRvadapterBinding
import java.text.DecimalFormat

class BookmarkRVAdapter(val context: Context, val listData : ArrayList<Memo>, var helper: SqliteHelper? = null)
    : RecyclerView.Adapter<BookmarkRVAdapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding =
            BookmarkRvadapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val memo: Memo = listData[position]
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Viewholder(private val binding: BookmarkRvadapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setMemo(memo: Memo) {

            binding.itemTitle.text = Html.fromHtml(memo.title.toString()).toString()
            binding.itemPrice.text =
                "최저가" + " " + DecimalFormat("###,###").format(memo.lprice) + "원"
            binding.itemMallname.text = memo.mallName
            binding.category1.text = memo.category1.toString()
            binding.category2.text = memo.category2.toString()
            binding.category3.text = memo.category3.toString()

            Glide.with(context)
                .load(memo.image)
                .into(binding.itemImage)

            if (binding.category3.text == "") {
                binding.sign.text = ""
            }

            itemView.setOnClickListener {
                val webpage = Uri.parse(
                    memo.link?.replace("search", "msearch")?.replace("gate.nhn?id=", "product/")
                )
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                context.startActivity(webIntent)

            }

            itemView.setOnLongClickListener {

                val builder = AlertDialog.Builder(context)
                builder.setTitle("알림")
                builder.setMessage("항목을 삭제하시겠습니까?")

                builder.setNegativeButton("아니오") { dialog, which ->

                }

                builder.setPositiveButton("네") { dialog, which ->
                    var cursor = adapterPosition

                    //강제로 null을 허용하기 위해 !! 사용
                    helper?.deleteMemo(listData[cursor])
                    listData.remove(listData[cursor])
                    notifyDataSetChanged()
                    Toast.makeText(context,"삭제 완료",Toast.LENGTH_SHORT).show()

                }

                builder.show()


                return@setOnLongClickListener (true)


            }


        }

    }
}