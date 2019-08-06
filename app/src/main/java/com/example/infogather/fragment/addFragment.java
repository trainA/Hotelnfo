package com.example.infogather.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infogather.DAO;
import com.example.infogather.Data;
import com.example.infogather.R;
import com.example.infogather.hotelDataAdapt;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link addFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link addFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int addCount;
    Button ButOk,ButSet;
    ListView lvAddDataShow;
    EditText editHotelName,editHOtelRomeName,editdevicenumber,editRemark;
    EditText editHotelAdds,editHotelTel;
    TextView tvAddCount;
    DAO management;
    private hotelDataAdapt mArrayAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public addFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addFragment newInstance(String param1, String param2) {
        addFragment fragment = new addFragment();
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
        return inflater.inflate(R.layout.fragment_add, container, false);
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
    private List<Map<String,Object>> mlist;
    private SimpleAdapter adapter;
    void Init()
    {
        addCount = 0;
        ButOk = getActivity().findViewById(R.id.btn_ok);
        ButSet = getActivity().findViewById(R.id.btn_set);
        management = new DAO(getContext());
        editdevicenumber = getActivity().findViewById(R.id.edit_device_number);
        editHotelName =  getActivity().findViewById(R.id.edit_hotel_name);
        editHOtelRomeName = getActivity().findViewById(R.id.edit_hotel_room_name);
        editHotelAdds = getActivity().findViewById(R.id.edit_hotel_location);
        editHotelTel = getActivity().findViewById(R.id.edit_phone);

        editHotelAdds.setText("暂时不可使用");
        editHotelTel.setText("暂时不可使用");
        editHotelTel.setFocusableInTouchMode(false);//设置为不可编辑
        editHotelAdds.setFocusableInTouchMode(false);

        editRemark = getActivity().findViewById(R.id.edit_remark);

        lvAddDataShow = getActivity().findViewById(R.id.listView1);
        tvAddCount = getActivity().findViewById(R.id.tv_add_count);
        //mArrayAdapter = new SimpleAdapter(getContext(),hotelDataList);
        mlist = new ArrayList<>();
//        Map<String,Object> map;
//        for(int i=0;i<100;i++){
//            map = new HashMap<>();
//            map.put("tv1","酒店名--"+(i+1));
//            map.put("tv2","房间号--"+(i+1));
//            map.put("tv3","设备号--"+(i+1));
//            map.put("tv4","备注--"+(i+1));
//            mlist.add(map);
//        }
        adapter = new SimpleAdapter(getContext(),mlist,R.layout.layout_item,new String[]{"tv1","tv2","tv3","tv4","tv5"},
                new int[]{R.id.tv_addday,R.id.tv_addtime,R.id.tv_hotelname,R.id.tv_hotelroomname,R.id.tv_device_number});
        lvAddDataShow.setAdapter(adapter);
        lvAddDataShow.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Map<String,Object> map = mlist.get(position);

//                Toast.makeText(getContext(),"长按"+position,Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
                builder.setTitle("提示" ) ;
                builder.setMessage("删除这条数据吗 ？\n"+map.get("tv3") + "房间号："+map.get("tv4") ) ;
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        long ans = management.deleteByHotelNameinHotelRomm(map.get("tv1").toString(),map.get("tv2").toString(),map.get("tv3").toString(),map.get("tv4").toString());
                        if(ans > 0)
                        {
                            mlist.remove(position);
                            adapter.notifyDataSetChanged();//刷新数据

                            ReduceEquipments(0);
                            if(ans >1)
                            {
                                Toast.makeText(getContext(),"删除数据未知错误删除了： " + Long.toString(ans)+"个数据",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(),"删除失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
                return true;
            }
        });
    }
    void AddEquipments(int v)
    {
        ++addCount;
        tvAddCount.setText(Integer.toString(addCount) + "      ");
    }
    void ReduceEquipments(int v)
    {
        if(addCount <= 0)return ;
        --addCount;
        tvAddCount.setText(Integer.toString(addCount) + "      ");
    }
    String getNowDay()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    String getNowTime()
    {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    long getNowTimeValue()
    {
        Date date = new Date(System.currentTimeMillis());
        return date.getTime();
    }
    Data getData()
    {

        String remark =  editRemark.getText().toString();
        if(editHotelName.getText().toString().isEmpty()  == true||
                editHOtelRomeName.getText().toString().isEmpty() == true||
                editdevicenumber.getText().toString().isEmpty() == true
        )
        {
            return null;
        }
        Data data = new Data();
        if(remark.isEmpty())remark = "无";
        data.setId(getNowTimeValue());
        data.setTime(getNowTime());
        data.setDay(getNowDay());
        data.setHotelname(editHotelName.getText().toString());
        data.setHotelroomname(editHOtelRomeName.getText().toString());
        data.setDevicenumber(editdevicenumber.getText().toString());
        data.setRemake(remark);
        Log.e("添加的数据为：", "getData: "+data.getId()+" "+data.getTime()+" "
        + data.getHotelname()+" "+data.getHotelroomname()+" "+data.getDevicenumber()+" "+data.getRemake());
        return data;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Init();
        ButOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data = getData();
                if(data == null)
                {
                    Toast.makeText(getActivity(),"添加失败",Toast.LENGTH_LONG).show();
                    return ;
                }
                management.add(data);
                editHOtelRomeName.setText("");
                editdevicenumber.setText("");
                editRemark.setText("");
                Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_LONG).show();
                Map<String,Object> map;
                map = new HashMap<>();

                map.put("tv1",data.getDay());
                map.put("tv2",data.getTime());
                map.put("tv3",data.getHotelname());
                map.put("tv4",data.getHotelroomname());
                map.put("tv5",data.getDevicenumber());
                mlist.add(map);
                adapter.notifyDataSetChanged();//刷新数据
                AddEquipments(0);
            }
        });
        ButSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
