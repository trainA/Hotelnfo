package com.example.infogather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Created by James on 2017/12/20.
 */

public class DAO {
    private Context context;
    /**
     * 需要上下文变量 操作数据库
     * @param context
     */
    public DAO(Context context) {
        this.context = context;
    }

    public long add(Data obj) {
        long id=-1;
        //1.实例化数据库操作助手对象
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();
        //3.准备相关参数
//        String sql=String.format("insert into students(name,age,clazz) values('%s',%d,'%s');",
//                student.getName(),student.getAge(),student.getClazz());
//        db.execSQL(sql);
        ContentValues values=new ContentValues();
        values.put(Database.HotelInfo.Column.ID,obj.getId());
        values.put(Database.HotelInfo.Column.ADDDAY,obj.getDay());
        values.put(Database.HotelInfo.Column.ADDTIME,obj.getTime());
        values.put(Database.HotelInfo.Column.HOTELNAME,obj.getHotelname());
        values.put(Database.HotelInfo.Column.HOTELROOMNAME,obj.getHotelroomname());
        values.put(Database.HotelInfo.Column.DEVICENUMBER,obj.getDevicenumber());
        values.put(Database.HotelInfo.Column.REMARK,obj.getRemake());

        //4.执行插入
        id=db.insert(Database.HotelInfo.TABLE_NAME,null,values);
        //5.关闭数据库
        db.close();
        return id;
    }
    public long updateById(Data obj) {
        long id=-1;
        //1.实例化数据库操作助手对象
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();
        //3.准备相关参数
//        String sql=String.format("insert into students(name,age,clazz) values('%s',%d,'%s');",
//                student.getName(),student.getAge(),student.getClazz());
//        db.execSQL(sql);
        ContentValues values=new ContentValues();
        values.put(Database.HotelInfo.Column.ID,obj.getId());
        values.put(Database.HotelInfo.Column.ADDDAY,obj.getDay());

        values.put(Database.HotelInfo.Column.ADDTIME,obj.getTime());
        values.put(Database.HotelInfo.Column.HOTELNAME,obj.getHotelname());
        values.put(Database.HotelInfo.Column.HOTELROOMNAME,obj.getHotelroomname());
        values.put(Database.HotelInfo.Column.DEVICENUMBER,obj.getDevicenumber());
        values.put(Database.HotelInfo.Column.REMARK,obj.getRemake());

        String whereClause="id=?";
        String[] whereArgs=new String[]{""+obj.getId()};
        //4.执行更新
        id=db.update(Database.HotelInfo.TABLE_NAME,values,whereClause,whereArgs);
        //5.关闭数据库
        db.close();
        return id;
    }
    public long deleteByHotelNameinHotelRomm(String addDay,String addTime,String hotelName,String hotelRoom)
    {
        long ret=-1;
        //1.实例化数据库操作助手对象
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();

        String whereClause="addday = ? and addtime = ? and hotelname = ? and hotelroomname = ?";
        String[] whereArgs=new String[]{ addDay,addTime,hotelName,hotelRoom};
        //4.执行更新
        ret=db.delete(Database.HotelInfo.TABLE_NAME,whereClause,whereArgs);
        //5.关闭数据库
        db.close();
        return ret;
    }
    public long deleteById(long id) {
        long ret=-1;
        //1.实例化数据库操作助手对象
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();

        String whereClause="id=?";
        String[] whereArgs=new String[]{""+id};
        //4.执行更新
        ret=db.delete(Database.HotelInfo.TABLE_NAME,whereClause,whereArgs);
        //5.关闭数据库
        db.close();
        return ret;
    }
    public List<Data> list(String whereClause,String[] whereArgs){
        List<Data> list=new ArrayList<>();
        //1.实例化数据库操作助手对象
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();
        String tableName= Database.HotelInfo.TABLE_NAME;
        String[] columns={
                Database.HotelInfo.Column.ID,
                Database.HotelInfo.Column.ADDDAY,
                Database.HotelInfo.Column.ADDTIME,
                Database.HotelInfo.Column.HOTELNAME,
                Database.HotelInfo.Column.HOTELROOMNAME,
                Database.HotelInfo.Column.DEVICENUMBER,
                Database.HotelInfo.Column.REMARK
        };
        //        Cursor query(String table, String[] columns, String selection,
        //         String[] selectionArgs, String groupBy, String having,
        //         String orderBy)
        //执行查询
        //第一个参数是表明，第二个参数是列名数组，第三个参数是查询条件，第四个参数是查询条件的参数
        //第五个参数是分组条件，第六个参数是分组条件的过滤条件，第7个参数是排序参数
        Cursor cursor=db.query(tableName,columns,whereClause,whereArgs,
                null,null,Database.HotelInfo.Column.ID+" DESC");
        if(cursor.getCount()==0) {
            db.close();
            return list;
        }
        //遍历结果集，返回对象列表
        while (cursor.moveToNext()){
            Data obj=new Data();
            obj.setId(cursor.getLong(0));
            obj.setTime(cursor.getString(1));
            obj.setHotelname(cursor.getString(2));
            obj.setHotelname(cursor.getString(3));
            obj.setDevicenumber(cursor.getString(4));

            list.add(obj);
        };
        //5.关闭数据库
        db.close();
        return list;
    }
    public ArrayList<Data>QureyHotelNameAndTime(String HotelName,String StartDay,String EndDay,String StartTime,String EndTime)
    {
        DBOpenHelper helper=new DBOpenHelper(context);
//        //2.获取Sqlite DB的实例
//        StringBuilder sb = new StringBuilder();
//        Formatter formatter = new Formatter(sb, Locale.US);
//        formatter.format("select *from hotelInfo\n" +
//                        "where (addday > %s and addday < %s and hotelname = %s)\n" +
//                        "or (addday =  %s and addtime >=  %s  and addday <>  %s and hotelname = %s  ) \n" +
//                        "or (addday =  %s  and addtime < %s and addday <>  %s and hotelname = %s) \n" +
//                        "or (addday =  %s  and addtime >=  %s  and addtime <=  %s and hotelname = %s)", StartDay,EndDay,HotelName,
//                StartDay,StartTime,EndDay,HotelName,
//                        EndDay, EndTime,StartDay,HotelName,
//                        StartDay,StartTime,EndTime,HotelName);
//
//        Log.e("sql按酒店名导出", formatter.toString()  );
        SQLiteDatabase db= helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select *from hotelInfo\n" +
                "where (addday > ? and addday < ? and hotelname = ?)\n" +
                "or (addday =  ? and addtime >=  ?  and addday <>  ? and hotelname = ?  ) \n" +
                "or (addday =  ?  and addtime < ? and addday <>  ? and hotelname = ?) \n" +
                "or (addday =  ?  and addtime >=  ?  and addtime <=  ? and hotelname = ?)", new String[]{StartDay,EndDay,HotelName,
                                                                                            StartDay,StartTime,EndDay,HotelName,
                                                                                            EndDay, EndTime,StartDay,HotelName,

                                                                           StartDay,StartTime,EndTime,HotelName});

        ArrayList<Data> ans = getData(cursor);
        db.close();
        return ans;
    }
    public ArrayList<Data> QueryAppointTimeData(String StartDay,String EndDay,String StartTime,String EndTime){


        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();

//        Cursor cursor = db.rawQuery("select *from hotelInfo\n" +
//                "where addday > ? and addday < ? \n" +
//                "or (addday =  ? and addtime >=  ?  and addday <>  ?  ) \n" +
//                "or (addday =  ?  and addtime < ? and addday <>  ? ) \n" +
//                "or (addday =  ?  and addtime >=  ?  and addtime <=  ? )", new String[]{StartDay,EndDay,
//                                                                                            StartDay,StartTime,EndDay,
//                                                                                            EndDay, EndTime,StartDay,
//
//                                                                           StartDay,StartTime,EndTime});
//        StringBuilder sb = new StringBuilder();
//// Send all output to the Appendable object sb
//        Formatter formatter = new Formatter(sb, Locale.US);
//        formatter.format("select *from hotelInfo\n" +
//                "where addday > '%s' and addday < '%s' \n" +
//                "or (addday = '%s' and addtime >= '%s' and addday <> '%s' ) \n" +
//                "or (addday = '%s' and addtime <=  '%s' and addday <> '%s') \n" +
//                "or (addday = '%s' and addtime >=  '%s' and addtime <= '%s')",         StartDay,EndDay,
//                StartDay,StartTime,EndDay,
//                EndDay,EndTime,StartDay,
//                StartDay,StartTime,EndTime
//        );
//        Log.e("sql", formatter.toString() );
        Cursor cursor = db.rawQuery("select *from hotelInfo\n" +
                "where addday > ? and addday < ? \n" +
                "or (addday = ? and addtime >= ? and addday <> ? ) \n" +
                "or (addday = ? and addtime <=  ? and addday <> ?) \n" +
                "or (addday = ? and addtime >=  ? and addtime <= ?)",
                new String[]{StartDay,EndDay,
                        StartDay,StartTime,EndDay,
                        EndDay,EndTime,StartDay,
                        StartDay,StartTime,EndTime
                    }
                );
        Log.e("查询结果数量", "" + cursor.getCount() );
        ArrayList<Data> ans = getData(cursor);
        db.close();
        return ans;
    }
    ArrayList<Data> getData(Cursor cursor)
    {
        ArrayList<Data> ans = new ArrayList<>();
        while (cursor.moveToNext()) {
            Data obj=new Data();
            obj.setId(cursor.getLong(cursor.getColumnIndex(Database.HotelInfo.Column.ID)));
            obj.setDay(cursor.getString(cursor.getColumnIndex(Database.HotelInfo.Column.ADDDAY)));
            obj.setTime(cursor.getString(cursor.getColumnIndex(Database.HotelInfo.Column.ADDTIME)));
            obj.setHotelname(cursor.getString(cursor.getColumnIndex(Database.HotelInfo.Column.HOTELNAME)));
            obj.setHotelroomname(cursor.getString(cursor.getColumnIndex(Database.HotelInfo.Column.HOTELROOMNAME)));
            obj.setDevicenumber(cursor.getString(cursor.getColumnIndex(Database.HotelInfo.Column.DEVICENUMBER)));
            obj.setRemake(cursor.getString(cursor.getColumnIndex(Database.HotelInfo.Column.REMARK)));
//            Log.e("要保存的数据为：", "getData: "+obj.getId()+" "+obj.getDay()+" "
//                    + obj.getHotelname()+" "+obj.getHotelroomname()+" "+obj.getDevicenumber()+" "+obj.getRemake());
            ans.add(obj);
        }
        return ans;
    }
    public ArrayList<String> getByHotelCount()
    {
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();
        Cursor cursor =db.query(true,Database.HotelInfo.TABLE_NAME,new String[]{"hotelname"},null,null,null,null,null,null);
       ArrayList<String> ans = new ArrayList<>();
        while (cursor.moveToNext())
        {
            ans.add(cursor.getString(0));
        }
        return ans;
    }
    //根据ID获取Student实例
    public Data getById(long id){
        //根据指定条件获取Student列表
        List<Data> list=list("id=?",new String[]{id+""});
        return list.size()>0?list.get(0):null;
    }
    public  ArrayList<Data> getByDeviceNumber(String deviceNum)
    {
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select *from hotelInfo \n" +
                "where devicenumber like ?",new String[]{"%"+deviceNum+"%"});
        ArrayList<Data> ans = getData(cursor);
        db.close();
        return ans;
    }
    public  ArrayList<Data> getByHotelNameAndDevice(String HotelName,String DeviceNum)
    {
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from hotelInfo\n" +
                "where devicenumber like ? and hotelname like ?",new String[]{"%"+DeviceNum+"%","?"+HotelName+"?"});
        ArrayList<Data> ans = getData(cursor);
        db.close();
        return ans;
    }
    public  ArrayList<Data> getByHotelName(String HotelName)
    {
        DBOpenHelper helper=new DBOpenHelper(context);
        //2.获取Sqlite DB的实例
        SQLiteDatabase db= helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select *from hotelInfo \n" +
                "where hotelname like ?",new String[]{"%"+HotelName+"%"});
        ArrayList<Data> ans = getData(cursor);
        db.close();
        return ans;
    }
}
