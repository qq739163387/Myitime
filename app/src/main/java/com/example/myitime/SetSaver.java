package com.example.myitime;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SetSaver {
    public Context getContext() {
        return context;
    }

    Context context;

    public ArrayList<Set> getBooks() {
        return sets;
    }

    ArrayList<Set>sets=new ArrayList<Set>();
    public void save(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE)
            );
            outputStream.writeObject(sets);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Set> load() {
        try{
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt")
            );
            sets = (ArrayList<Set>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    public SetSaver(ArrayList<Set> books) {
        this.sets = sets;
    }

    public SetSaver(Context context) {
        this.context = context;
    }
}
