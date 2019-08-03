package com.example.infogather;

/**
 * Created by James on 2018/9/14.
 */

public class Data {
    private long id;//ID
    private String day;//ID
    private String time;//Time
    private String hotelname;
    private String hotelroomname;
    private String devicenumber;
    private String remake;

    public  Data(long id,String time,String hotelname,String hotelroomname,String devicenumber)
    {
        this.id = id;
        this.time = time;
        this.hotelname = hotelname;
        this.hotelroomname = hotelroomname;
        this.devicenumber = devicenumber;
    }
    public Data()
    {

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDevicenumber(String devicenumber) {
        this.devicenumber = devicenumber;
    }

    public String getDevicenumber() {
        return devicenumber;
    }

    public String getHotelname() {
        return hotelname;
    }

    public String getHotelroomname() {
        return hotelroomname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public void setHotelroomname(String hotelroomname) {
        this.hotelroomname = hotelroomname;
    }
}
