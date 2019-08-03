package com.example.infogather;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.infogather.hotelData;

import java.util.List;

public class hotelDataAdapt extends ArrayAdapter {

    private Context context;
    private List<hotelData> datas;

    public hotelDataAdapt(Context context, int resource,List<hotelData> objects) {
        super(context, resource, objects);
        this.datas = objects;
    }

//    public hotelDataAdapt(Context context,List<hotelData> datas) {
//        this.context=context;
//        this.datas = datas;
//    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_item,null);
            holder.tvHotelName=convertView.findViewById(R.id.tv_hotelname);
            holder.tvHotelRoomName=convertView.findViewById(R.id.edit_hotel_room_name);
            holder.tvdeviceNumber=convertView.findViewById(R.id.edit_device_number);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        hotelData data=datas.get(position);
        holder.tvHotelName.setText(data.getHotelname());
        holder.tvHotelRoomName.setText(data.getHotelroomname()+"");
        holder.tvdeviceNumber.setText(data.getDevicenumber()+"");
        holder.tvRemark.setText(data.getRemake()+"");
        return convertView;


    }
    class ViewHolder{
        TextView tvHotelName;
        TextView tvHotelRoomName;
        TextView tvdeviceNumber;
        TextView tvRemark;

    }
}
