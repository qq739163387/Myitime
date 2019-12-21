package com.example.myitime;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.myitime.MainActivity.listdates;

public class EditActivity extends AppCompatActivity {
    private FloatingActionButton FabBack2;
    private FloatingActionButton FabOK2;
    private EditText TitleText2,DescriptionText2;
    private int editPosition2;
    private ArrayList<Set> listsets;
    private ListView listViewsets2;
    private static SetAdapter theSetadapter;
    private int mYear,mMonth,mDay,minute,hour,second,mYearset,mMonthset,mDayset,minuteset,hourset,NEWDAY,NEWMONTH,NEWYEAR,NEWHOUR,NEWMINUTE;
    private Set SetAtPosition;
    private Set DateSetAtPosition;
    private String days;
    private SetSaver setSaver;
    private int daysnum2;
    private ImageView editimageview;
    private Bitmap editbitmap;

    private AlertDialog alertDialog;
    private int zhouqiset;
    private Set Zhouqiset;
    private Set Datezhouqiset;

    //下面传入图片所用变量
    private static final int CHOOSE_PHOTO2=102;
    private ImageView photo;
    private Bitmap myBitmap;
    private byte[] mContent;
    private ByteArrayOutputStream baos;
    private byte [] bitmapByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        //获取时间
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        hour=ca.get(Calendar.HOUR_OF_DAY);
        minute=ca.get(Calendar.MINUTE);
        second=ca.get(Calendar.SECOND);

        //初始化
        FabBack2=this.findViewById(R.id.floatingActionButtonback2);
        FabOK2=this.findViewById(R.id.floatingActionButtonok2);
        TitleText2=this.findViewById(R.id.titleText2);
        DescriptionText2=this.findViewById(R.id.descriptionText2);
        editimageview=this.findViewById(R.id.imageView2);

        editPosition2=getIntent().getIntExtra("edit_position",0);

            String string1 = listdates.get(editPosition2).getName();
            String string2 = listdates.get(editPosition2).getTime();

                TitleText2.setText(string1);
                DescriptionText2.setText(string2);

        editimageview.setImageResource(listdates.get(editPosition2).getPicture());
        listViewsets2=this.findViewById(R.id.listview_set2);

        listsets=new ArrayList<Set>();
        listsets.add(new Set("日期",R.drawable.clock,"点击修改日期"));
        listsets.add(new Set("周期",R.drawable.xun,"点击修改周期"));
        listsets.add(new Set("上传图片",R.drawable.photo,"图片功能尚未完善"));

        theSetadapter=new SetAdapter(this,R.layout.list_item_set,listsets);
        listViewsets2.setAdapter(theSetadapter);
        this.registerForContextMenu(listViewsets2);

        //直接用对应日期初始化
        DateSetAtPosition=getListSets().get(0);
        DateSetAtPosition.setText(listdates.get(editPosition2).getTargetyear()+"年"+listdates.get(editPosition2).getTargetmonth()+"月"+
                listdates.get(editPosition2).getTargetday()+"日 "+listdates.get(editPosition2).getTargethour()+":"+listdates.get(editPosition2).getTargetminute());

        //初始化周期
        Datezhouqiset=getListSets().get(1);
        if (listdates.get(editPosition2).getZhouqi()==1) {
            Datezhouqiset.setText("每日");
        }
        if (listdates.get(editPosition2).getZhouqi()==7) {
            Datezhouqiset.setText("每周");
        }
        if (listdates.get(editPosition2).getZhouqi()==30) {
            Datezhouqiset.setText("每月");
        }
        if (listdates.get(editPosition2).getZhouqi()==364) {
            Datezhouqiset.setText("每年");
        }

        //时间设置
        final TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int iminute) {

                //设置目标日期
                NEWHOUR=hourOfDay;
                NEWMINUTE=iminute;
                hourset=hourOfDay-hour;
                minuteset=iminute-minute;

                if (mMonthset  < 10) {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYearset).append("年").append("0").
                                append(mMonthset ).append("月").append("0").append(mDayset).append("日").append(hourset).append("时").
                                append(minuteset).append("分").toString();
                    } else {
                        days = new StringBuffer().append(mYearset).append("年").append("0").
                                append(mMonthset ).append("月").append(mDayset).append("日").append(hourset).append("时").
                                append(minuteset).append("分").toString();
                    }

                } else {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYearset).append("年").
                                append(mMonthset ).append("月").append("0").append(mDayset).append("日").append(hourset).append("时").
                                append(minuteset).append("分").toString();
                    } else {
                        days = new StringBuffer().append(mYearset).append("年").
                                append(mMonthset ).append("月").append(mDayset).append("日").append(hourset).append("时").
                                append(minuteset).append("分").toString();
                    }

                }
                SetAtPosition.setText(NEWYEAR+"年"+NEWMONTH+"月"+NEWDAY+"日 "+hourOfDay+":"+iminute);
                theSetadapter.notifyDataSetChanged();

            }
        };

        //日期设置
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                NEWDAY=dayOfMonth;
                NEWMONTH=monthOfYear+1;
                NEWYEAR=year;
                //下面为计算总日数
                if (monthOfYear-mMonth==0&&dayOfMonth-mDay>=0) {
                    mYearset = year - mYear;
                    mMonthset = monthOfYear - mMonth ;
                    mDayset = dayOfMonth - mDay;
                    daysnum2=mDayset;
                }
                else if (monthOfYear-mMonth<0&&dayOfMonth-mDay>=0){
                    mYearset = year - mYear-1;
                    mMonthset = monthOfYear - mMonth +12;
                    mDayset = dayOfMonth - mDay;
                    int guo=0,wei=0;
                    if (year%4==0){
                        switch(monthOfYear+1){
                            case 1:
                                wei=dayOfMonth;
                                break;
                            case 2:
                                wei=dayOfMonth+31;
                                break;
                            case 3:
                                wei=dayOfMonth+60;
                                break;
                            case 4:
                                wei=dayOfMonth+91;
                                break;
                            case 5:
                                wei=dayOfMonth+121;
                                break;
                            case 6:
                                wei=dayOfMonth+152;
                                break;
                            case 7:
                                wei=dayOfMonth+182;
                                break;
                            case 8:
                                wei=dayOfMonth+213;
                                break;
                            case 9:
                                wei=dayOfMonth+243;
                                break;
                            case 10:
                                wei=dayOfMonth+274;
                                break;
                            case 11:
                                wei=dayOfMonth+304;
                                break;
                            case 12:
                                wei=dayOfMonth+335;
                                break;
                        }
                    }else{
                        switch(monthOfYear+1) {
                            case 1:
                                wei = dayOfMonth;
                                break;
                            case 2:
                                wei = dayOfMonth + 31;
                                break;
                            case 3:
                                wei = dayOfMonth + 59;
                                break;
                            case 4:
                                wei = dayOfMonth + 90;
                                break;
                            case 5:
                                wei = dayOfMonth + 120;
                                break;
                            case 6:
                                wei = dayOfMonth + 151;
                                break;
                            case 7:
                                wei = dayOfMonth + 181;
                                break;
                            case 8:
                                wei = dayOfMonth + 212;
                                break;
                            case 9:
                                wei = dayOfMonth + 242;
                                break;
                            case 10:
                                wei = dayOfMonth + 273;
                                break;
                            case 11:
                                wei = dayOfMonth + 303;
                                break;
                            case 12:
                                wei = dayOfMonth + 334;
                                break;
                        }
                    }
                    if (mYear%4==0){
                        switch(mMonth+1){
                            case 1:
                                guo=mDay;
                                guo=365-guo;
                                break;
                            case 2:
                                guo=mDay+31;
                                guo=365-guo;
                                break;
                            case 3:
                                guo=mDay+60;
                                guo=365-guo;
                                break;
                            case 4:
                                guo=mDay+91;
                                guo=365-guo;
                                break;
                            case 5:
                                guo=mDay+121;
                                guo=365-guo;
                                break;
                            case 6:
                                guo=mDay+152;
                                guo=365-guo;
                                break;
                            case 7:
                                guo=mDay+182;
                                guo=365-guo;
                                break;
                            case 8:
                                guo=mDay+213;
                                guo=365-guo;
                                break;
                            case 9:
                                guo=mDay+243;
                                guo=365-guo;
                                break;
                            case 10:
                                guo=mDay+274;
                                guo=365-guo;
                                break;
                            case 11:
                                guo=mDay+304;
                                guo=365-guo;
                                break;
                            case 12:
                                guo=mDay+335;
                                guo=365-guo;
                                break;
                        }
                    }else{
                        switch(mMonth+1) {
                            case 1:
                                guo = mDay;
                                guo=365-guo;
                                break;
                            case 2:
                                guo = mDay + 31;
                                guo=365-guo;
                                break;
                            case 3:
                                guo = mDay + 59;
                                guo=365-guo;
                                break;
                            case 4:
                                guo = mDay + 90;
                                guo=365-guo;
                                break;
                            case 5:
                                guo = mDay + 120;
                                guo=365-guo;
                                break;
                            case 6:
                                guo = mDay + 151;
                                guo=365-guo;
                                break;
                            case 7:
                                guo = mDay + 181;
                                guo=365-guo;
                                break;
                            case 8:
                                guo = mDay + 212;
                                guo=365-guo;
                                break;
                            case 9:
                                guo = mDay + 242;
                                guo=365-guo;
                                break;
                            case 10:
                                guo = mDay + 273;
                                guo=365-guo;
                                break;
                            case 11:
                                guo = mDay + 303;
                                guo=365-guo;
                                break;
                            case 12:
                                guo = mDay + 334;
                                guo=365-guo;
                                break;
                        }
                    }
                    daysnum2=wei+guo+mYearset*364;
                }
                else if (monthOfYear-mMonth>0&&dayOfMonth-mDay<0){
                    if (mMonth+1==1||mMonth+1==3||mMonth+1==5||mMonth+1==7||mMonth+1==9||mMonth+1==11) {
                        mYearset = year-mYear;
                        mMonthset = monthOfYear - mMonth - 1;
                        mDayset = 31 - mDay + dayOfMonth;
                    } else if (mMonth==2&&year%4==0){
                        mYearset = year-mYear;
                        mMonthset = monthOfYear - mMonth - 1;
                        mDayset = 29 - mDay + dayOfMonth;
                    } else if (mMonth==2&&year%4!=0){
                        mYearset = year-mYear;
                        mMonthset = monthOfYear - mMonth - 1;
                        mDayset = 28 - mDay + dayOfMonth;
                    }else {
                        mYearset = year-mYear;
                        mMonthset = monthOfYear - mMonth -1;
                        mDayset = 30 - mDay + dayOfMonth;
                    }
                    int guo=0,wei=0;
                    if (year%4==0){
                        switch(monthOfYear+1){
                            case 1:
                                wei=dayOfMonth;
                                break;
                            case 2:
                                wei=dayOfMonth+31;
                                break;
                            case 3:
                                wei=dayOfMonth+60;
                                break;
                            case 4:
                                wei=dayOfMonth+91;
                                break;
                            case 5:
                                wei=dayOfMonth+121;
                                break;
                            case 6:
                                wei=dayOfMonth+152;
                                break;
                            case 7:
                                wei=dayOfMonth+182;
                                break;
                            case 8:
                                wei=dayOfMonth+213;
                                break;
                            case 9:
                                wei=dayOfMonth+243;
                                break;
                            case 10:
                                wei=dayOfMonth+274;
                                break;
                            case 11:
                                wei=dayOfMonth+304;
                                break;
                            case 12:
                                wei=dayOfMonth+335;
                                break;
                        }
                    }else{
                        switch(monthOfYear+1) {
                            case 1:
                                wei = dayOfMonth;
                                break;
                            case 2:
                                wei = dayOfMonth + 31;
                                break;
                            case 3:
                                wei = dayOfMonth + 59;
                                break;
                            case 4:
                                wei = dayOfMonth + 90;
                                break;
                            case 5:
                                wei = dayOfMonth + 120;
                                break;
                            case 6:
                                wei = dayOfMonth + 151;
                                break;
                            case 7:
                                wei = dayOfMonth + 181;
                                break;
                            case 8:
                                wei = dayOfMonth + 212;
                                break;
                            case 9:
                                wei = dayOfMonth + 242;
                                break;
                            case 10:
                                wei = dayOfMonth + 273;
                                break;
                            case 11:
                                wei = dayOfMonth + 303;
                                break;
                            case 12:
                                wei = dayOfMonth + 334;
                                break;
                        }
                    }
                    if (mYear%4==0){
                        switch(mMonth+1){
                            case 1:
                                guo=mDay;

                                break;
                            case 2:
                                guo=mDay+31;

                                break;
                            case 3:
                                guo=mDay+60;

                                break;
                            case 4:
                                guo=mDay+91;

                                break;
                            case 5:
                                guo=mDay+121;

                                break;
                            case 6:
                                guo=mDay+152;

                                break;
                            case 7:
                                guo=mDay+182;

                                break;
                            case 8:
                                guo=mDay+213;

                                break;
                            case 9:
                                guo=mDay+243;

                                break;
                            case 10:
                                guo=mDay+274;

                                break;
                            case 11:
                                guo=mDay+304;

                                break;
                            case 12:
                                guo=mDay+335;

                                break;
                        }
                    }else{
                        switch(mMonth+1) {
                            case 1:
                                guo = mDay;
                                guo=365-guo;
                                break;
                            case 2:
                                guo = mDay + 31;
                                guo=365-guo;
                                break;
                            case 3:
                                guo = mDay + 59;
                                guo=365-guo;
                                break;
                            case 4:
                                guo = mDay + 90;
                                guo=365-guo;
                                break;
                            case 5:
                                guo = mDay + 120;
                                guo=365-guo;
                                break;
                            case 6:
                                guo = mDay + 151;
                                guo=365-guo;
                                break;
                            case 7:
                                guo = mDay + 181;
                                guo=365-guo;
                                break;
                            case 8:
                                guo = mDay + 212;
                                guo=365-guo;
                                break;
                            case 9:
                                guo = mDay + 242;
                                guo=365-guo;
                                break;
                            case 10:
                                guo = mDay + 273;
                                guo=365-guo;
                                break;
                            case 11:
                                guo = mDay + 303;
                                guo=365-guo;
                                break;
                            case 12:
                                guo = mDay + 334;
                                guo=365-guo;
                                break;
                        }
                    }
                    daysnum2=wei-guo+364*mYearset;
                }
                else if (monthOfYear-mMonth==0&&dayOfMonth-mDay<0){
                    if (mMonth+1==1||mMonth+1==3||mMonth+1==5||mMonth+1==7||mMonth+1==9||mMonth+1==11){
                        mYearset = year - mYear-1;
                        mMonthset=11;
                        mDayset = 31 - mDay + dayOfMonth;
                    }else if (mMonth+1==2&&year%4==0){
                        mYearset = year-mYear;
                        mMonthset = 11;
                        mDayset = 29 - mDay + dayOfMonth;
                    } else if (mMonth+1==2&&year%4!=0){
                        mYearset = year-mYear;
                        mMonthset =11;
                        mDayset = 28 - mDay + dayOfMonth;
                    }else {
                        mYearset = year-mYear;
                        mMonthset = 11;
                        mDayset = 30 - mDay + dayOfMonth;
                    }
                    daysnum2=364*mYearset-(dayOfMonth-mDay);
                }
                else if (monthOfYear-mMonth<0&&dayOfMonth-mDay<0){
                    if (mMonth+1==1||mMonth+1==3||mMonth+1==5||mMonth+1==7||mMonth+1==9||mMonth+1==11){
                        mYearset = year - mYear-1;
                        mMonthset=11+monthOfYear-mMonth;
                        mDayset = 31 - mDay + dayOfMonth;
                    }else if (mMonth+1==2&&year%4==0){
                        mYearset = year-mYear-1;
                        mMonthset = 11+monthOfYear-mMonth;
                        mDayset = 29 - mDay + dayOfMonth;
                    } else if (mMonth+1==2&&year%4!=0){
                        mYearset = year-1;
                        mMonthset =11+monthOfYear-mMonth;
                        mDayset = 28 - mDay + dayOfMonth;
                    }else {
                        mYearset = year-mYear-1;
                        mMonthset = 11+monthOfYear-mMonth;
                        mDayset = 30 - mDay + dayOfMonth;
                    }
                    int guo=0,wei=0;
                    if (year%4==0){
                        switch(monthOfYear+1){
                            case 1:
                                wei=dayOfMonth;
                                break;
                            case 2:
                                wei=dayOfMonth+31;
                                break;
                            case 3:
                                wei=dayOfMonth+60;
                                break;
                            case 4:
                                wei=dayOfMonth+91;
                                break;
                            case 5:
                                wei=dayOfMonth+121;
                                break;
                            case 6:
                                wei=dayOfMonth+152;
                                break;
                            case 7:
                                wei=dayOfMonth+182;
                                break;
                            case 8:
                                wei=dayOfMonth+213;
                                break;
                            case 9:
                                wei=dayOfMonth+243;
                                break;
                            case 10:
                                wei=dayOfMonth+274;
                                break;
                            case 11:
                                wei=dayOfMonth+304;
                                break;
                            case 12:
                                wei=dayOfMonth+335;
                                break;
                        }
                    }else{
                        switch(monthOfYear+1) {
                            case 1:
                                wei = dayOfMonth;
                                break;
                            case 2:
                                wei = dayOfMonth + 31;
                                break;
                            case 3:
                                wei = dayOfMonth + 59;
                                break;
                            case 4:
                                wei = dayOfMonth + 90;
                                break;
                            case 5:
                                wei = dayOfMonth + 120;
                                break;
                            case 6:
                                wei = dayOfMonth + 151;
                                break;
                            case 7:
                                wei = dayOfMonth + 181;
                                break;
                            case 8:
                                wei = dayOfMonth + 212;
                                break;
                            case 9:
                                wei = dayOfMonth + 242;
                                break;
                            case 10:
                                wei = dayOfMonth + 273;
                                break;
                            case 11:
                                wei = dayOfMonth + 303;
                                break;
                            case 12:
                                wei = dayOfMonth + 334;
                                break;
                        }
                    }
                    if (mYear%4==0){
                        switch(mMonth+1){
                            case 1:
                                guo=mDay;
                                guo=365-guo;
                                break;
                            case 2:
                                guo=mDay+31;
                                guo=365-guo;
                                break;
                            case 3:
                                guo=mDay+60;
                                guo=365-guo;
                                break;
                            case 4:
                                guo=mDay+91;
                                guo=365-guo;
                                break;
                            case 5:
                                guo=mDay+121;
                                guo=365-guo;
                                break;
                            case 6:
                                guo=mDay+152;
                                guo=365-guo;
                                break;
                            case 7:
                                guo=mDay+182;
                                guo=365-guo;
                                break;
                            case 8:
                                guo=mDay+213;
                                guo=365-guo;
                                break;
                            case 9:
                                guo=mDay+243;
                                guo=365-guo;
                                break;
                            case 10:
                                guo=mDay+274;
                                guo=365-guo;
                                break;
                            case 11:
                                guo=mDay+304;
                                guo=365-guo;
                                break;
                            case 12:
                                guo=mDay+335;
                                guo=365-guo;
                                break;
                        }
                    }else{
                        switch(mMonth+1) {
                            case 1:
                                guo = mDay;
                                guo=365-guo;
                                break;
                            case 2:
                                guo = mDay + 31;
                                guo=365-guo;
                                break;
                            case 3:
                                guo = mDay + 59;
                                guo=365-guo;
                                break;
                            case 4:
                                guo = mDay + 90;
                                guo=365-guo;
                                break;
                            case 5:
                                guo = mDay + 120;
                                guo=365-guo;
                                break;
                            case 6:
                                guo = mDay + 151;
                                guo=365-guo;
                                break;
                            case 7:
                                guo = mDay + 181;
                                guo=365-guo;
                                break;
                            case 8:
                                guo = mDay + 212;
                                guo=365-guo;
                                break;
                            case 9:
                                guo = mDay + 242;
                                guo=365-guo;
                                break;
                            case 10:
                                guo = mDay + 273;
                                guo=365-guo;
                                break;
                            case 11:
                                guo = mDay + 303;
                                guo=365-guo;
                                break;
                            case 12:
                                guo = mDay + 334;
                                guo=365-guo;
                                break;
                        }
                    }
                    daysnum2=wei+guo+mYearset*364;
                }
                else{
                    mYearset=year-mYear;
                    mMonthset=monthOfYear-mMonth;
                    mDayset=dayOfMonth-mDay;

                    daysnum2=mYearset*364+mMonthset*30+mDayset;
                }

                //调用时钟
                new TimePickerDialog(EditActivity.this,onTimeSetListener,minute,hour,true).show();
            }
        };

        listViewsets2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置日期item点击事件和显示内容
                SetAtPosition=getListSets().get(position);
                if (position==0) {
                    new DatePickerDialog(EditActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                    //new TimePickerDialog(Add_Activity.this,onTimeSetListener,minute,hour,true).show();
                    theSetadapter.notifyDataSetChanged();
                }
                if (position==1)
                {
                    Zhouqiset=getListSets().get(position);
                    showList(view);
                    //theSetadapter.notifyDataSetChanged();
                }
                //下面为图片上传事件
            /*    if (position==2){
                   File outputImage =new File(getExternalCacheDir(),"output_image.jpg");
                    try{
                        if (outputImage.exists()){
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT>=24){
                        imageUri= FileProvider.getUriForFile(Add_Activity.this,"com.example.myitime",outputImage);
                    }else{
                        imageUri=Uri.fromFile(outputImage);
                    }
                    //启动相机程序
                    Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,903);
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, CHOOSE_PHOTO2);
                } */
                //Toast.makeText(DateSetActivity.this,"您单击了确定"+position, Toast.LENGTH_SHORT).show();
            }
        });

        FabOK2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                update(zhouqiset);
                Intent intent =new Intent();   //在事件函数中新建Intent对象
                //intent.putExtra("edit_position",editPosition2);
                intent.putExtra("title",TitleText2.getText().toString());    //把要传回去的数据打包
                intent.putExtra("Desc",DescriptionText2.getText().toString());    //把要传回去的数据打包
                intent.putExtra("years",mYearset+"");                               //传回年差数
                intent.putExtra("months",mMonthset+"");                               //传回月差数
                intent.putExtra("days",mDayset+"");                               //传回日差数
                intent.putExtra("hours",hourset+"");                               //传回小时差数
                intent.putExtra("minutes",minuteset+"");                               //传回分钟差数
                intent.putExtra("newyear",NEWYEAR+"");                                 //传回目的年数
                intent.putExtra("newmonth",NEWMONTH+"");                              //传回目的月数
                intent.putExtra("newday",NEWDAY+"");                              //传回目的月数
                intent.putExtra("newhour",NEWHOUR+"");                                     //传回目的小时
                intent.putExtra("newminute",NEWMINUTE+"");                                 //传回目的分钟
                intent.putExtra("zhouqiedit",zhouqiset+"");                                   //传回周期
                intent.putExtra("daysnumber",daysnum2+"");                                     //传回总日数

                //listdates.get(editPosition2).setDaysnumber(daysnum2);
                setResult(RESULT_OK,intent);     //调用setResult设置处理状态和intent
                EditActivity.this.finish();   //调用finish函数关闭本界面
            }
        });

        FabBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //Intent intent =new Intent();   //在事件函数中新建Intent对象
                //intent.putExtra("title",editTextBookTitle.getText().toString());    //把要传回去的数据打包
                //setResult(Result_OK,intent);     //调用setResult设置处理状态和intent
                EditActivity.this.finish();   //调用finish函数关闭本界面
            }
        });
    }
    public List<Set> getListSets(){
        return listsets;
    }

    //当设置了周期之后数据进行更新
    private void update(int zhouqiset1){
        if (mYear>NEWYEAR||(mMonth>=NEWMONTH&&mDay>=NEWDAY&&mYear==NEWYEAR)){
            if (zhouqiset1==1){
                NEWYEAR=mYear;
                NEWMONTH=mMonth+1;
                NEWDAY=mDay+1;
            }
            if (zhouqiset1==7){
                NEWYEAR=mYear;
                NEWMONTH=mMonth+1;
                NEWDAY=mDay+7;
                if (NEWDAY>30&&(NEWMONTH==4||NEWMONTH==6||NEWMONTH==8||NEWMONTH==10)){
                    NEWDAY=NEWDAY-30;
                    NEWMONTH=NEWMONTH+1;
                    if (NEWMONTH>12){
                        NEWYEAR=NEWYEAR+1;
                        NEWMONTH=NEWMONTH-12;
                    }
                }
            }
            if (zhouqiset1==30){
                NEWYEAR=mYear;
                NEWMONTH=mMonth+1+1;
                NEWDAY=mDay;
                if (NEWMONTH>12){
                    NEWYEAR=NEWYEAR+1;
                    NEWMONTH=NEWMONTH-12;
                }
            }
            if (zhouqiset1==364){
                NEWYEAR=mYear+1;
                NEWMONTH=mMonth+1;
                NEWDAY=mDay;
            }
        }
    }

    //图片响应事件
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ContentResolver resolver=getContentResolver();    //获取当前context；

        switch (requestCode) {
            case CHOOSE_PHOTO2:
              /*  if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        bitmap = ImgUtil.handleImageOnKitKat(this, data);        //ImgUtil是自己实现的一个工具类
                    } else {
                        //4.4以下系统使用这个方法处理图片
                        bitmap = ImgUtil.handleImageBeforeKitKat(this, data);
                    }
                    //ImageView view = (ImageView) findViewById(R.id.imageViewphoto);
                    photo.setImageBitmap(bitmap);
                }*/

                //将uri转化为数组转化为bitmap
                try {
                    Uri originaUri = data.getData();
                    mContent=readStream(resolver.openInputStream(Uri.parse(originaUri.toString())));
                    myBitmap=getPicFromButes(mContent,null);
                    photo.setImageBitmap(myBitmap);
                    listdates.get(editPosition2).setBitmap(myBitmap);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //第一种方法，拥有裁剪框
             /*  Uri uri=data.getData();
               crop(uri);
             */
                break;
            case 101:
                // 从剪切图片返回的数据
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    photo.setImageBitmap(bitmap);
                }
                           /* try {
                               // 将临时文件删除
                               tempFile.delete();
                         } catch (Exception e) {
                               e.printStackTrace();
                         }*/
            default:
                break;
        }
    }

    //列表框
    public void showList(View view){
        final String[] items = {"每日", "每周", "每月", "每年"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择周期");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    zhouqiset=1;
                    Zhouqiset.setText("每日");
                    theSetadapter.notifyDataSetChanged();
                }else if (i==1){
                    zhouqiset=7;
                    Zhouqiset.setText("每周");
                    theSetadapter.notifyDataSetChanged();
                }else if (i==2){
                    zhouqiset=30;
                    Zhouqiset.setText("每月");
                    theSetadapter.notifyDataSetChanged();
                }else if (i==3){
                    zhouqiset=364;
                    Zhouqiset.setText("每年");
                    theSetadapter.notifyDataSetChanged();
                }else{
                    zhouqiset=0;
                    Toast.makeText(EditActivity.this, "请选择周期", Toast.LENGTH_SHORT).show();}
                alertDialog.dismiss();
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 380);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, 101);
    }

    //将uri字节数组转化为bitmap
    public static Bitmap getPicFromButes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null) {
            if (opts != null) {
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            } else return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
    }

    //获取uri的字节
    public  static byte[] readStream(InputStream inStream)throws Exception{
        byte[] buffer=new byte[1024];
        int len=-1;
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        while ((len=inStream.read(buffer))!=-1){
            outStream.write(buffer,0,len);
        }
        byte[] data=outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }
}
