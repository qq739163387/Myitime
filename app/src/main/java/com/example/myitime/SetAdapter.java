package com.example.myitime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

public class SetAdapter extends ArrayAdapter<Set> {

    public SetAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Set> objects) {
        super(context, resource, objects);

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Set set_item=getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_set,parent,false);

        ImageView img = (ImageView)view.findViewById(R.id.detail_set);
        TextView name = (TextView)view.findViewById(R.id.date_name2_textView);
        TextView date = (TextView)view.findViewById(R.id.datetextView);

        img.setImageResource(set_item.getPictureId());
        name.setText(set_item.getSetting());
        date.setText(set_item.getDate());

        return view;
    }
}
