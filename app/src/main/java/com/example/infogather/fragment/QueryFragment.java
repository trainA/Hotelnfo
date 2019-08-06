package com.example.infogather.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.infogather.DAO;
import com.example.infogather.Data;
import com.example.infogather.MainActivity;
import com.example.infogather.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QueryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QueryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button BtnQueryData;
    EditText editDevice,editHotelName;
    DAO management;
    private OnFragmentInteractionListener mListener;

    public QueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QueryFragment newInstance(String param1, String param2) {
        QueryFragment fragment = new QueryFragment();
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
        return inflater.inflate(R.layout.fragment_query, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    void init()
    {
        management = new DAO(getContext());
        BtnQueryData = getActivity().findViewById(R.id.btn_query_data);
        editDevice = getActivity().findViewById(R.id.edit_device_number_find);
        editHotelName = getActivity().findViewById(R.id.edit_hotel_name_find);

    }
    void listen()
    {
        BtnQueryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deviceNum = editDevice.getText().toString();
                if(deviceNum.isEmpty() == false)
                {
                    ArrayList<Data> ans = management.getByDeviceNumber(deviceNum);
                    Log.e("查询到的数据", Integer.toString(ans.size()) );
                    dialogList();
                    if(ans.size() >0)
                    {
                        Toast.makeText(getContext(),"查询到"+Integer.toString(ans.size())+"条数据",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    private void dialogList() {
        final String items[] = {"列表1", "列表2", "列表3", "列表4"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),0);
        builder.setTitle("查询到的设备");
//        builder.setIcon(R.mipmap.ic_launcher);
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        listen();
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
}
