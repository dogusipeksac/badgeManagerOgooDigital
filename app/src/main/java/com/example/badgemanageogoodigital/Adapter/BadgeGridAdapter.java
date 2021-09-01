package com.example.badgemanageogoodigital.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.R;
import com.example.badgemanageogoodigital.Service.JsonService;

import java.io.IOException;
import java.io.InputStream;
    
public class BadgeGridAdapter extends BaseAdapter {
    Context context;
    private JsonService service;

    public class ViewHolder{

        ImageView imageView;
        TextView title;
        RatingBar ratingBar;
        TextView howManydata;
    }
    private BadgeData[] items;
    private LayoutInflater mInflater;


    public BadgeGridAdapter(Context context, BadgeData[] locations){
        mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        items=locations;
        service=JsonService.get(context);

    }


    @Override
    public int getCount() {
        if(items!= null){
            return items.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (items!=null && position>=0 && position < getCount()){
            return items[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (items!=null && position>=0 && position < getCount()){
            return items[position].getId();
        }
        return 0;
    }
    //Viewe set ediyoruz listemizdeki verileri
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder;
        if(view==null){
            view=mInflater.inflate(R.layout.badge_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView=view.findViewById(R.id.badgesImage);
            viewHolder.title=view.findViewById(R.id.badgeTitle);
            viewHolder.ratingBar=view.findViewById(R.id.ratingBarInBadge);
            viewHolder.howManydata=view.findViewById(R.id.howmanydata);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }
        BadgeData gridItems=items[position];
        viewHolder.title.setText(gridItems.getTitle());
        //burada id ye göre resimleri atıyorum
        InputStream ims = null;
        try {
            ims = context.getAssets().open("resource/image"+gridItems.getId()+".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  Drawable dan image çekmek
        Drawable d = Drawable.createFromStream(ims, null);
        viewHolder.imageView.setImageDrawable(d);
        viewHolder.howManydata.setText(service.calculateSize(gridItems.getId())+" adet");
        viewHolder.ratingBar.setRating(service.calculateAvarage(gridItems.getId()));

        return view;
    }


}
