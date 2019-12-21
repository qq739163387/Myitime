package com.example.myitime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.myitime.MainActivity.listdates;
import static com.example.myitime.MainActivity.theadapter;

public class DetailActivity extends AppCompatActivity {

    private TextView zhuti;
    private TextView countdown;
    private TextView countdown2;
    private ImageButton rubishbutton;
    private ImageButton editbutton;
    private ImageButton backbutton;
    private ImageView iv;
    private int detailposition;
    private String detailname;
    private int detailtargetyear;
    private int detailtargetmonth;
    private int detailtargetday;
    private int detailtargethour;
    private int detailtargetminute;
    private int detailyear;
    private int detailmonth;
    private int detailday;
    private int detailhour;
    private int detailminute;
    private int detailsecond;
    private int detailpicture;
    private long Countdowntime;
    private CountDownTimer countDownTimer;
    private int daysnumber;
    private SparseArray<CountDownTimer> countDownCounters;
    private DateSaver dateSaver;
    private Bitmap detailbitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        zhuti=this.findViewById(R.id.zhuti );
        countdown=this.findViewById(R.id.countdown);
        countdown2=this.findViewById(R.id.countdown2);
        rubishbutton=this.findViewById(R.id.deleteButton);
        editbutton=this.findViewById(R.id.editButton);
        backbutton=this.findViewById(R.id.backButton);
        iv=this.findViewById(R.id.pictureshow);

        detailpicture=getIntent().getIntExtra("picture",0);
        detailposition=getIntent().getIntExtra("position",0);
        detailname=getIntent().getStringExtra("name");
        detailtargetyear=getIntent().getIntExtra("targetyear",0);
        detailtargetmonth=getIntent().getIntExtra("targetmonth",0);
        detailtargetday=getIntent().getIntExtra("targetday",0);
        detailtargethour=getIntent().getIntExtra("targethour",0);
        detailtargetminute=getIntent().getIntExtra("targetminute",0);
        detailyear=getIntent().getIntExtra("year",0);
        detailmonth=getIntent().getIntExtra("month",0);
        detailday=getIntent().getIntExtra("day",0);
        detailhour=getIntent().getIntExtra("hour",0);
        detailminute=getIntent().getIntExtra("minute",0);
        detailsecond=getIntent().getIntExtra("second",0);
        daysnumber=getIntent().getIntExtra("dayszongshu",0);
       // byte[] bis = getIntent().getByteArrayExtra("bitmap");
       // detailbitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);             //传送失败
        //detailbitmap=listdates.get(detailposition).getBitmap();

        //展示标题
        zhuti.setText(detailname);
        //展示目的日期数
        countdown2.setText(detailtargetday+"号"+detailtargetmonth+"月"+detailtargetyear+"年"+detailtargethour+":"+detailtargetminute);
        //展示图片
        iv.setImageResource(detailpicture);
        //设置开始倒数时间
        Countdowntime=(long)(daysnumber*60*60*24+detailhour*60*60+detailminute*60+detailsecond)*1000;

        countDownTimer = new CountDownTimer(Countdowntime,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
               // countdown.setText(TimeUtils.getCountTimeByLong(millisUntilFinished));
                long day = millisUntilFinished / (1000 * 24 * 60 * 60); //单位天

                long hour = (millisUntilFinished - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60);
                //单位时
                long minute = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60);
                //单位分
                long second = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                //单位秒
                countdown.setText(day+"天"+hour + "小时" + minute + "分钟" + second + "秒");

               // listdates.get(detailposition).setDaysnumber((int)day);
               // listdates.get(detailposition).setHous((int)hour);
               // listdates.get(detailposition).setMinutes((int)minute);
               // listdates.get(detailposition).setSeconds((int)second);

            }

            @Override
            public void onFinish() {
                countdown.setText("00:00:00");
                //Toast.makeText(convertView.getContext(), "倒计时完成", Toast.LENGTH_SHORT).show();
            }
        };
        countDownTimer.start();

        //返回按钮
        backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                theadapter.notifyDataSetChanged();
                DetailActivity.this.finish();
            }
        });

        //删除按钮
        rubishbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Dialog dialog =new AlertDialog.Builder(view.getContext())
                        .setTitle("删除itime吗？")
                        .setMessage("您确定要删除这条itime吗？")
                        .setPositiveButton("删除",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                                listdates.remove(detailposition);
                                theadapter.notifyDataSetChanged();
                                DetailActivity.this.finish();
                                //listBooks.remove(removePosition);
                                //bookAdapter.notifyDataSetChanged();
                                //Toast.makeText(BookListMainActivity.this,"删除成功",Toast.LENGTH_LONG);
                            }
                        })
                        .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                            }
                        }).create();
                        dialog.show();
               // DetailActivity.this.finish();
            }
        });
         //编辑按钮
        editbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(DetailActivity.this,EditActivity.class);
                intent.putExtra("edit_position", detailposition);
                startActivityForResult(intent,902);
               // DetailActivity.this.finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       // dateSaver.save();
        cancelAllTimers();
    }

    /**
     * 清空当前 CountTimeDown 资源
     */
    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 902:
                if (resultCode == RESULT_OK) {
                    //int position = data.getIntExtra("edit_position", 0);
                    String name = data.getStringExtra("title");
                    String time = data.getStringExtra("Desc");
                    String yearstring = data.getStringExtra("years");
                    int yearint = Integer.parseInt(yearstring);
                    String monthstring = data.getStringExtra("months");
                    int monthint = Integer.parseInt(monthstring);
                    String daystring = data.getStringExtra("days");
                    int dayint = Integer.parseInt(daystring);
                    String targetyearstring = data.getStringExtra("newyear");
                    int targetyearint = Integer.parseInt(targetyearstring);
                    String targetmonthstring = data.getStringExtra("newmonth");
                    int targetmonthint = Integer.parseInt(targetmonthstring);
                    String targetdaystring = data.getStringExtra("newday");
                    int targetdayint = Integer.parseInt(targetdaystring);
                    String targethourstring = data.getStringExtra("newhour");
                    int targethourint = Integer.parseInt(targethourstring);
                    String targetminutestring = data.getStringExtra("newminute");
                    int targetminuteint = Integer.parseInt(targetminutestring);
                    String hourstring = data.getStringExtra("hours");
                    int hourint = Integer.parseInt(hourstring);
                    String minutestring = data.getStringExtra("minutes");
                    int minuteint = Integer.parseInt(minutestring);
                    String daysnumstring = data.getStringExtra("daysnumber");
                    int daysnumint=Integer.parseInt(daysnumstring);
                    String zhouqistring = data.getStringExtra("zhouqiedit");
                    int zhouqiint=Integer.parseInt(zhouqistring);
                    Date dateAtposition = listdates.get(detailposition);
                    dateAtposition.setName(name);
                    dateAtposition.setTime(time);
                    dateAtposition.setTargetyear(targetyearint);
                    dateAtposition.setTargetday(targetdayint);
                    dateAtposition.setTargethour(targethourint);
                    dateAtposition.setTargetminute(targetminuteint);
                    dateAtposition.setTargetmonth(targetmonthint);
                    dateAtposition.setYears(yearint);
                    dateAtposition.setMonths(monthint);
                    dateAtposition.setDays(dayint);
                    dateAtposition.setHous(hourint);
                    dateAtposition.setMinutes(minuteint);
                    dateAtposition.setDaysnumber(daysnumint);
                    dateAtposition.setZhouqi(zhouqiint);


                    detailname=name;
                    detailtargetyear=targetyearint;
                    detailtargetmonth=targetmonthint;
                    detailtargetday=targetdayint;
                    detailtargethour=targethourint;
                    detailtargetminute=targetminuteint;
                    detailyear=yearint;
                    detailmonth=monthint;
                    detailday=dayint;
                    detailhour=hourint;
                    detailminute=minuteint;
                    daysnumber=daysnumint;
                    zhuti.setText(detailname);
                    countdown2.setText(detailtargetday+"号"+detailtargetmonth+"月"+detailtargetyear+"年"+detailtargethour+":"+detailtargetminute);

                    theadapter.notifyDataSetChanged();

                }
                break;
        }
    }
}
