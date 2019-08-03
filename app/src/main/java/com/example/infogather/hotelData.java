package com.example.infogather;

public class hotelData {
    private String hotelname;
    private String hotelroomname;
    private String devicenumber;
    private String remake;

    public hotelData(String hotelname,String hotelroomname,String devicenumber,String remake)
    {
        this.hotelname = hotelname;
        this.hotelroomname = hotelroomname;
        this.devicenumber = devicenumber;
        this.remake = remake;
    }
    public hotelData()
    {

    }
    public String getRemake() {
        return remake;
    }

    public String getHotelroomname() {
        return hotelroomname;
    }

    public String getHotelname() {
        return hotelname;
    }

    public String getDevicenumber() {
        return devicenumber;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public void setHotelroomname(String hotelroomname) {
        this.hotelroomname = hotelroomname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public void setDevicenumber(String devicenumber) {
        this.devicenumber = devicenumber;
    }
}
