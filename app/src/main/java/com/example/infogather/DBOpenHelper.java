package com.example.infogather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by James on 2017/12/20.
 */

public class DBOpenHelper extends SQLiteOpenHelper{
    //建表语句，建议一定要测试
    public static final String CRT_TBL_STUDENTS=
          "CREATE TABLE hotelInfo (\n" +
                  "    id            INTEGER      NOT NULL\n" +
                  "                               PRIMARY KEY,\n" +
                  "    addday        DATE         NOT NULL,\n" +
                  "    addtime       TIME         NOT NULL,\n" +
                  "    hotelname     VARCHAR (50) NOT NULL,\n" +
                  "    hotelroomname VARCHAR (10) NOT NULL,\n" +
                  "    devicenumber  VARCHAR (20) NOT NULL,\n" +
                  "    remark        VARCHAR (50) NOT NULL\n" +
                  ");";
    public static final String INS_TBL_STUDENTS=
            "insert into hotelInfo(id,addday,addtime,hotelname,hotelroomname,devicenumber,remark) values(1,'1999-09-13','13:54:01','test','test','test','无')";

    public DBOpenHelper(Context context) {
        //调用父类的构造函数，明确数据库的名称和版本
        super(context, "data.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //当数据库创建的时候，自动完成建库、建表、数据初始化
        db.execSQL(CRT_TBL_STUDENTS);
        db.execSQL(INS_TBL_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
