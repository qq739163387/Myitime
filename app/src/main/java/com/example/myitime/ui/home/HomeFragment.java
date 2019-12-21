package com.example.myitime.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myitime.Add_Activity;
import com.example.myitime.Date;
import com.example.myitime.DateAdapter;
import com.example.myitime.DateSaver;
import com.example.myitime.DetailActivity;
import com.example.myitime.MainActivity;
import com.example.myitime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.myitime.MainActivity.theadapter;
import static com.example.myitime.MainActivity.listdates;
//import static com.example.myitime.MainActivity.dateSaver;

public class HomeFragment extends Fragment {
    private ListView listviewdate;
    private View view;
   // private ArrayList<Date> listdates;
    private HomeViewModel homeViewModel;
    //下面为图片传送变量
    private ByteArrayOutputStream baos;
    private byte [] bitmapByte;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);

        listviewdate=(ListView)view.findViewById(R.id.listViewdate);
        listviewdate.setAdapter(theadapter);
        //this.registerForContextMenu(listviewdate);
        //item点击响应
        listviewdate.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // String text = listviewdate.getItemAtPosition(position) + "";
                Intent intent=new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("position",position);
                int picture=listdates.get(position).getPicture();
                intent.putExtra("picture",picture);              //图片
                String zhuti=listdates.get(position).getName();
                intent.putExtra("name",zhuti);                  //标题
                int targetyear=listdates.get(position).getTargetyear();    //目标年份
                intent.putExtra("targetyear",targetyear);
                int targetmonth=listdates.get(position).getTargetmonth();   //目标月份
                intent.putExtra("targetmonth",targetmonth);
                int targetday=listdates.get(position).getTargetday();     //目标日期
                intent.putExtra("targetday",targetday);
                int targethour=listdates.get(position).getTargethour();     //目标小时
                intent.putExtra("targethour",targethour);
                int targetminute=listdates.get(position).getTargetminute();     //目标分钟
                intent.putExtra("targetminute",targetminute);
                int yearhome=listdates.get(position).getYears();      //相差年份
                intent.putExtra("year",yearhome);
                int monthhome=listdates.get(position).getMonths();    //相差月份
                intent.putExtra("month",monthhome);
                int dayhome=listdates.get(position).getDays();    //相差日数
                intent.putExtra("day",dayhome);
                int hourhome=listdates.get(position).getHous();    //相差时数
                intent.putExtra("hour",hourhome);
                int minutehome=listdates.get(position).getMinutes();  //相差分钟数
                intent.putExtra("minute",minutehome);
                int  secondhome=listdates.get(position).getSeconds();    //相差秒数
                intent.putExtra("second",secondhome);
                int dayshome=listdates.get(position).getDaysnumber();     //总日数
                intent.putExtra("dayszongshu",dayshome);

                //传送失败
               /* Bitmap bitmap=listdates.get(position).getBitmap();        //传送bitmap
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                bitmapByte =baos.toByteArray();
                intent.putExtra("bitmap", bitmapByte);*/
                startActivity(intent);

            }
        });
        return view;
    }
}