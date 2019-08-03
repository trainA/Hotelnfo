package com.example.infogather;

/**
 * Created by James on 2017/12/20.
 */

public class Database {
    public static final class HotelInfo{
        //表名
        public static final String TABLE_NAME="hotelInfo";
        public static final class Column{//字段名
            public static final String  ID="id";
            public static final String  ADDDAY="addday";
            public static final String  ADDTIME="addtime";
            public static final String  HOTELNAME="hotelname";
            public static final String  HOTELROOMNAME="hotelroomname";
            public static final String  DEVICENUMBER="devicenumber";
            public static final String  REMARK = "remark";
        }
    }
}
