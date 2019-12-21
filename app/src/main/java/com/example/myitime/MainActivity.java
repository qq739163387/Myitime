package com.example.myitime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.myitime.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.myitime.R.layout.list_item;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FloatingActionButton Fab;
    private ListView listviewdate;
    public static DateAdapter theadapter;
    public static List<Date> listdates;
    private DateSaver dateSaver;

    private AdapterView.OnItemClickListener mOnItemClick;

    public List<Date> getListdates(){
        return listdates;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Add_Activity.class);
                intent.putExtra("insert_position",0);
                startActivityForResult(intent, 901);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //constra=LayoutInflater.from();

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //------------------------------------------------分割线---------------------------------------------------

        listdates = new ArrayList<>();
        if (listdates.size()==0)
            init();
        theadapter = new DateAdapter(this, R.layout.list_item, listdates);


        //item点击监听
    /*    listviewdate.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = listviewdate.getItemAtPosition(position) + "";
                //Toast.makeText(this, "position=" + position + "text=" + text, Toast.LENGTH_SHORT);
                Toast.makeText(MainActivity.this,"您单击了确定", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        dateSaver.save();
    }


    //点击item响应跳转到详细页面
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 901:
                if(resultCode==RESULT_OK) {
                    // int position=data.getIntExtra("edit_position",0);
                    String name = data.getStringExtra("title");                     //标题
                    String time = data.getStringExtra("Desc");                     //标签
                    String yearstring = data.getStringExtra("years");               //相差年数
                    int yearint = Integer.parseInt(yearstring);
                    String monthstring = data.getStringExtra("months");             //相差月数
                    int monthint = Integer.parseInt(monthstring);
                    String daystring = data.getStringExtra("days");                 //相差日数
                    int dayint = Integer.parseInt(daystring);
                    String targetyearstring = data.getStringExtra("newyear");           //目标年数
                    int targetyearint = Integer.parseInt(targetyearstring);
                    String targetmonthstring = data.getStringExtra("newmonth");           //目标月数
                    int targetmonthint = Integer.parseInt(targetmonthstring);
                    String targetdaystring = data.getStringExtra("newday");                //目标日数
                    int targetdayint = Integer.parseInt(targetdaystring);
                    String targethourstring = data.getStringExtra("newhour");              //目标小时
                    int targethourint = Integer.parseInt(targethourstring);
                    String targetminutestring = data.getStringExtra("newminute");            //目标分钟
                    int targetminuteint = Integer.parseInt(targetminutestring);
                    String hourstring = data.getStringExtra("hours");                 //相差小时
                    int hourint = Integer.parseInt(hourstring);
                    String minutestring = data.getStringExtra("minutes");              //相差分钟
                    int minuteint = Integer.parseInt(minutestring);
                    String daysnumstring = data.getStringExtra("daysnum");          //两个日期之间相差总天数
                    int daysnumint = Integer.parseInt(daysnumstring);
                    String zhouqistring = data.getStringExtra("zhouqi");          //周期
                    int zhouqi = Integer.parseInt(zhouqistring);

                    //设定图片显示内容
                        if (listdates.size() == 0) {
                            listdates.add(0, new Date(name, time, R.drawable.a11, yearint, monthint, dayint, hourint, minuteint, targetyearint,
                                    targetmonthint, targetdayint, targethourint, targetminuteint, daysnumint,zhouqi));
                        } else if (listdates.size() == 1) {
                            listdates.add(0, new Date(name, time, R.drawable.a12, yearint, monthint, dayint, hourint, minuteint, targetyearint,
                                    targetmonthint, targetdayint, targethourint, targetminuteint, daysnumint,zhouqi));
                        } else if (listdates.size() == 2) {
                            listdates.add(0, new Date(name, time, R.drawable.a13, yearint, monthint, dayint, hourint, minuteint, targetyearint,
                                    targetmonthint, targetdayint, targethourint, targetminuteint, daysnumint,zhouqi));
                        } else if (listdates.size() % 3 == 0) {
                            listdates.add(0, new Date(name, time, R.drawable.a14, yearint, monthint, dayint, hourint, minuteint, targetyearint,
                                    targetmonthint, targetdayint, targethourint, targetminuteint, daysnumint,zhouqi));
                        } else if (listdates.size() % 4 == 0) {
                            listdates.add(0, new Date(name, time, R.drawable.a15, yearint, monthint, dayint, hourint, minuteint, targetyearint,
                                    targetmonthint, targetdayint, targethourint, targetminuteint, daysnumint,zhouqi));
                        } else {
                            listdates.add(0, new Date(name, time, R.drawable.a16, yearint, monthint, dayint, hourint, minuteint, targetyearint,
                                    targetmonthint, targetdayint, targethourint, targetminuteint, daysnumint,zhouqi));
                        }

                    theadapter.notifyDataSetChanged();

                    dateSaver.save();
                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //当activity切换回来时刷新数据
    @Override
    protected void onResume() {
        super.onResume();
      theadapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //初始化
    private void init() {

        dateSaver=new DateSaver(this);
        listdates=dateSaver.load();

    }

}
