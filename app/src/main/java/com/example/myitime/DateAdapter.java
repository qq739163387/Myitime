package com.example.myitime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;



public class DateAdapter extends ArrayAdapter<Date> {

    private Context mContext;
    private List<Date>mDate;
    private SparseArray<CountDownTimer> countDownCounters;

    public DateAdapter(Context context, int resource, List<Date> objects) {
        super(context, resource, objects);

    }
    @NonNull
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        final Date date_item=getItem(position);

        View view =LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        ImageView img = (ImageView)view.findViewById(R.id.date_small_imageView);
        final TextView timeSet=(TextView)view.findViewById(R.id.timeset);
        TextView name = (TextView)view.findViewById(R.id.date_name_textView);
        TextView time = (TextView)view.findViewById(R.id.date_time_textView);
        TextView target=(TextView)view.findViewById(R.id.targetdate);
        TextView showday=(TextView)view.findViewById(R.id.dayshow);



        long Countdowntime=(long)(date_item.getDaysnumber()*60*60*24+date_item.getHous()*60*60+date_item.getMinutes()*60+date_item.getSeconds())*1000;
        final long count=0;

        CountDownTimer countDownTimer = new CountDownTimer(Countdowntime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long day = millisUntilFinished / (1000 * 24 * 60 * 60); //单位天

                long hour = (millisUntilFinished - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60);
                //单位时
                long minute = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60);
                //单位分
                long second = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                //单位秒
                //timeSet.setText(day+"天"+hour + "小时" + minute + "分钟" + second + "秒");
                //timeSet.setText(TimeUtils.getCountTimeByLong(millisUntilFinished));     //显示倒计时
                timeSet.setText("正在进行！");
               TimeUtils.getCountTimeByLong(millisUntilFinished);           //只是倒计时，不显示

                //判断一分钟过后刷新数据
                if (count==60000){
                    date_item.setDaysnumber(TimeUtils.getDayback());
                    date_item.setHous(TimeUtils.getHourback());
                    date_item.setHous(TimeUtils.getMinuteback());
                    date_item.setSeconds(TimeUtils.getSecondback());
                }
               //秒数每秒都在刷新为了和detail页面一致
                date_item.setSeconds(TimeUtils.getSecondback());
            }

            @Override
            public void onFinish() {
                date_item.setDaysnumber(date_item.getZhouqi());
                //周期为0并倒计时结束则显示end
                if (0==date_item.getZhouqi()) {
                    timeSet.setText("已经过期!");
                }
            }
        };

        if (countDownTimer != null) {
            //将复用的倒计时清除
            countDownTimer.cancel();
        }


        //判断倒计时是否结束
/*
        long timer = date_item.getTargetday();
        timer = timer - System.currentTimeMillis();
        //expirationTime 与系统时间做比较，timer 小于零，则此时倒计时已经结束。
        if (timer > 0) {
            countDownTimer = new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {
                    timeSet.setText(TimeUtils.getCountTimeByLong(millisUntilFinished));
                }
                public void onFinish() {
                    timeSet.setText("00:00:00");
                    Toast.makeText(convertView.getContext(), "倒计时完成", Toast.LENGTH_SHORT).show();
                }
            }.start();
            //将此 countDownTimer 放入list.
            countDownCounters.put(timeSet.hashCode(), countDownTimer);
        } else {
            timeSet.setText("00:00:00");

        }
*/

        img.setImageResource(date_item.getPicture());
        name.setText(date_item.getName());
        //timeSet.setText(date_item.getExpirationTime());
        target.setText(date_item.getExpirationTime());
        time.setText(date_item.getTime());
        showday.setText(date_item.getDaysnumber()+"DAYS");

        countDownTimer.start();
        return view;
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




}
