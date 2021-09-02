package com.example.badgemanageogoodigital.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class SpinnerAdapter extends ArrayAdapter<BadgeData> {


    public SpinnerAdapter(Context context, List<BadgeData> badgeArrayList) {
        super(context, 0,badgeArrayList);
    }

    @NonNull
    @Override
    public View getView(int position,
                        View convertView,
                        @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position,
                                 View convertView,
                                @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }
    private View initView(int position,View convertView,ViewGroup parent){
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                    parent,false);
        }
        ImageView imageViewBadge=convertView.findViewById(R.id.image_spinner);
        TextView textViewTitle=convertView.findViewById(R.id.text_spinner_title);
        BadgeData badgeItem=getItem(position);
        if(badgeItem!=null){
            //burada id ye göre resimleri atıyorum
            InputStream ims = null;
            try {
                ims = getContext().getAssets().open("resource/image"+badgeItem.getId()+".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //  Drawable dan image çekmek
            Drawable d = Drawable.createFromStream(ims, null);
            imageViewBadge.setImageDrawable(d);
            textViewTitle.setText(badgeItem.getTitle());
        }

        return convertView;
    }
}
