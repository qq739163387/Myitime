package com.example.myitime;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DateSaver {
    Context context;
    public Context getContext() {
        return context;
    }

    ArrayList<Date>dates=new ArrayList<Date>();
    public ArrayList<Date> getDates() {
        return dates;
    }

    public void save(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE)
            );
            outputStream.writeObject(dates);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Date> load() {
        try{
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt")
            );
            dates = (ArrayList<Date>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    public DateSaver(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public DateSaver(Context context) {
        this.context = context;
    }

}
