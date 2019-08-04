package com.example.infogather;

import android.app.AlertDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.feezu.liuli.timeselector.TimeSelector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link exportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link exportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class exportFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button ButOk;
    TextView tvStartTime,tvEndTime;
    RadioButton RbExportAllData,RbTodayData;
    CheckBox CbExportTime,CbExportHotelName;
    EditText EditHotelNmae;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public exportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment exportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static exportFragment newInstance(String param1, String param2) {
        exportFragment fragment = new exportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_export, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    Pair<String ,String>getStartandEndTime()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.add(Calendar.YEAR, -5);//当前时间减去一年，即一年前的时间
        String start = format.format(calendar.getTime());

        Calendar endtime = Calendar.getInstance(); //创建Calendar 的实例
        endtime.setTime(date);
        endtime.set(Calendar.MINUTE, 0);
        endtime.set(Calendar.HOUR, 0);
        endtime.add(Calendar.DATE, +1);//当前时间减去一年，即一年前的时间
        String end = format.format(endtime.getTime());
        return new Pair<>(start,end);
    }
    void Init()
    {
        tvStartTime = getActivity().findViewById(R.id.tv_start_time);
        tvEndTime = getActivity().findViewById(R.id.tv_end_time);
        ButOk = getActivity().findViewById(R.id.btn_query);
        EditHotelNmae = getActivity().findViewById(R.id.edit_hotel_name_export);
        RbExportAllData = getActivity().findViewById(R.id.rb_all_data);
        RbTodayData = getActivity().findViewById(R.id.rb_today);
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pair<String,String> time = getStartandEndTime();
                TimeSelector timeSelector = new TimeSelector(getContext(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tvEndTime.setText(time);
                    }
                }, time.first, time.second);
                timeSelector.setTitle("请选择结束时间");
                timeSelector.show();

            }
        });
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<String,String> time = getStartandEndTime();
                TimeSelector timeSelector = new TimeSelector(getContext(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tvStartTime.setText(time);
                    }
                }, time.first, time.second);
                timeSelector.setTitle("请选择开始时间");
                timeSelector.show();
            }
        });

        CbExportHotelName = getActivity().findViewById(R.id.ch_hotel_export);
        CbExportTime = getActivity().findViewById(R.id.ch_on_time);

    }
    Pair<String ,String>getStartandEndDayTime(String s)
    {
        String day;
        String time;
        int index = s.indexOf(' ');
        day = s.substring(0,index);
        time = s.substring(index+1,s.length())+":00";
        return new  Pair<>(day,time);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();


        ButOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   long count =  ExportDataONnExcel();
//                Pair<String,String>start = getStartandEndDayTime(tvStartTime.getText().toString());
//                Pair<String,String>end = getStartandEndDayTime(tvEndTime.getText().toString());
//                DAO dataQuery = new DAO(getContext());
//                List<Data> ans = dataQuery.QueryAppointTimeData(start.first,end.first,start.second ,end.second);
                if(count == 0)
                {
                    AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
                    builder.setTitle("提示" ) ;
                    builder.setMessage("查询结果为空" ) ;
                    builder.setPositiveButton("确定" ,  null );
                    builder.show();
                    return ;
                }
                else if(count>0)
                {
                    AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
                    builder.setTitle("提示" ) ;
                    builder.setMessage("查询完成总共有："+count+"条数据" ) ;
                    builder.setPositiveButton("确定" ,  null );
                    builder.show();
                }
                else
                {
                    Toast.makeText(getActivity(),"错误",Toast.LENGTH_LONG).show();
                }
//                try {
//                    ExcelUtils.writeExcel(getContext(),ans,"导出数据");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity(),"导出失败（文件无法创建）",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                Toast.makeText(getActivity(),"导出成功",Toast.LENGTH_LONG).show();

            }
        });
    }
    int ExportDataONnExcel()
    {
        if(CbExportHotelName.isChecked())//按酒店名导出
        {
            String HotelName = EditHotelNmae.getText().toString();
            Log.d("HotelName:",HotelName );
            Toast.makeText(getActivity(),"酒店名"+HotelName,Toast.LENGTH_LONG).show();
            if( HotelName == "")return -1;
            if(RbTodayData.isChecked() == true)
            {
                DAO dataQuery = new DAO(getContext());
                String time = getToday();
                List<Data> ans = dataQuery.QureyHotelNameAndTime(HotelName,time,time,"00:00:00" ,"23:59:59");
                if(ans.size() == 0)return 0;
                try {
                    ExcelUtils.writeExcel(getContext(),ans,"导出数据:"+HotelName+time);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"导出失败（文件无法创建）",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return -1;
                }
                Toast.makeText(getActivity(),"导出成功",Toast.LENGTH_LONG).show();
                return ans.size();
            }
            else if(RbExportAllData.isChecked())
            {
                DAO dataQuery = new DAO(getContext());
                String time = getToday();
                List<Data> ans = dataQuery.QureyHotelNameAndTime(HotelName,"2000-01-01",time,"00:00:00" ,"23:59:59");
                if(ans.size() == 0)return 0;
                try {
                    ExcelUtils.writeExcel(getContext(),ans,"导出数据:"+HotelName+"all");
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"导出失败（文件无法创建）",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return -1;
                }
                Toast.makeText(getActivity(),"导出成功",Toast.LENGTH_LONG).show();
                return ans.size();
            }
            else
            {
                DAO dataQuery = new DAO(getContext());
                Pair<String,String>start = getStartandEndDayTime(tvStartTime.getText().toString());
                Pair<String,String>end = getStartandEndDayTime(tvEndTime.getText().toString());
                List<Data> ans = dataQuery.QureyHotelNameAndTime(HotelName,start.first,end.first,start.second ,end.second);
                if(ans.size() == 0)return 0;
                try {
                    ExcelUtils.writeExcel(getContext(),ans,"导出数据:"+HotelName+start.first+"到"+end.first);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"导出失败（文件无法创建）",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return -1;
                }
                Toast.makeText(getActivity(),"导出成功",Toast.LENGTH_LONG).show();
                return ans.size();
            }
        }
        else //按时间导出
        {
            if(RbTodayData.isChecked() == true)
            {
                DAO dataQuery = new DAO(getContext());
                Log.e("按时间导出数据", "ExportDataONnExcel: "+getToday() );
                String time = getToday();
                List<Data> ans = dataQuery.QueryAppointTimeData(time,time,"00:00:00" ,"23:59:59");
                try {
                    ExcelUtils.writeExcel(getContext(),ans,"导出数据"+time);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"导出失败（文件无法创建）",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return -1;
                }
                Toast.makeText(getActivity(),"导出成功",Toast.LENGTH_LONG).show();
                return ans.size();
            }
            else if(RbExportAllData.isChecked() == true)
            {
                DAO dataQuery = new DAO(getContext());
                Log.e("导出全部数据", "ExportDataONnExcel: "+getToday() );
                String time = getToday();
                List<Data> ans = dataQuery.QueryAppointTimeData("2000-01-01",time,"00:00:00" ,"23:59:59");
                try {
                    ExcelUtils.writeExcel(getContext(),ans,"导出数据"+time+"alldata");
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"导出失败（文件无法创建）",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return -1;
                }
                Toast.makeText(getActivity(),"导出成功",Toast.LENGTH_LONG).show();
                return ans.size();
            }
            else
            {
                Pair<String,String>start = getStartandEndDayTime(tvStartTime.getText().toString());
                Pair<String,String>end = getStartandEndDayTime(tvEndTime.getText().toString());
                DAO dataQuery = new DAO(getContext());
                List<Data> ans = dataQuery.QueryAppointTimeData(start.first,end.first,start.second ,end.second);
                if(ans.size() == 0)return 0;
                try {
                    ExcelUtils.writeExcel(getContext(),ans,"导出数据:"+start.first + start.second + "--"+end.first+end.second);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"导出失败（文件无法创建）",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return -1;
                }
                Toast.makeText(getActivity(),"导出成功",Toast.LENGTH_LONG).show();
                return ans.size();
            }
        }
    }
    String getToday()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
//        return "2019-08-03";
        return format.format(date);
    }
}
