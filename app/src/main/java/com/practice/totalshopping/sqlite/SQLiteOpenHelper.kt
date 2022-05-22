package com.practice.totalshopping.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// SQLiteOpenHelper 상속받아 SQLite 를 사용하도록 하겠습니다.
class SqliteHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    //onCreate(), onUpgrade() 두가지 메소드를 오버라이드 받아 줍시다.

    //데이터베이스가 만들어 지지않은 상태에서만 작동합니다. 이미 만들어져 있는 상태라면 실행되지 않습니다.
    override fun onCreate(db: SQLiteDatabase?) {
        //테이블을 생성할 쿼리를 작성하여 줍시다.
        val create = "create table memo (title text primary key,link text,image text, lprice integer, mallName text, category1 text, category2 text, category3 text)"
        //실행시켜 줍니다.
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //insert 메소드
    fun insertMemo(memo: Memo){
        val values = ContentValues()
        //넘겨줄 컬럼의 매개변수 지정
        values.put("title",memo.title)
        values.put("link",memo.link)
        values.put("image",memo.image)
        values.put("lprice",memo.lprice)
        values.put("mallName",memo.mallName)
        values.put("category1",memo.category1)
        values.put("category2",memo.category2)
        values.put("category3",memo.category3)
        //쓰기나 수정이 가능한 데이터베이스 변수
        val wd = writableDatabase
        wd.insert("memo",null,values)
        //사용이 끝나면 반드시 close()를 사용하여 메모리누수 가 되지않도록 합시다.
        wd.close()
    }


    //select 메소드
    fun selectMemo():MutableList<Memo>{
        val list = mutableListOf<Memo>()
        //전체조회
        val selectAll = "select * from memo"
        //읽기전용 데이터베이스 변수
        val rd = readableDatabase
        //데이터를 받아 줍니다.
        val cursor = rd.rawQuery(selectAll,null)

        //반복문을 사용하여 list 에 데이터를 넘겨 줍시다.
        while(cursor.moveToNext()){
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val link = cursor.getString(cursor.getColumnIndexOrThrow("link"))
            val image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
            val lprice = cursor.getInt(cursor.getColumnIndexOrThrow("lprice"))
            val mallName = cursor.getString(cursor.getColumnIndexOrThrow("mallName"))
            val category1 = cursor.getString(cursor.getColumnIndexOrThrow("category1"))
            val category2 = cursor.getString(cursor.getColumnIndexOrThrow("category2"))
            val category3 = cursor.getString(cursor.getColumnIndexOrThrow("category3"))

            list.add(Memo(title,link,image,lprice,mallName,category1,category2,category3))
        }
        cursor.close()
        rd.close()

        return list
    }

    //update 메소드
    fun updateMemo(memo: Memo){
        val values = ContentValues()

        values.put("title",memo.title)
        values.put("link",memo.link)
        values.put("image",memo.image)
        values.put("lprice",memo.lprice)
        values.put("mallName",memo.mallName)
        values.put("category1",memo.category1)
        values.put("category2",memo.category2)
        values.put("category3",memo.category3)

        val wd = writableDatabase
        wd.update("memo",values,"title=${memo.title}",null)
        wd.close()

    }

    //delete 메소드
    fun deleteMemo(memo: Memo){
        val delete = "delete from Memo where lprice = ${memo.lprice}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()

    }

    fun deleteAllMemo(){
        val delete = "delete from Memo"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()

    }

}