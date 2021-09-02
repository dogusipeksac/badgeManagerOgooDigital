package com.example.badgemanageogoodigital.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.badgemanageogoodigital.Model.Data;
import com.example.badgemanageogoodigital.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Data> listItems;
    private Context context;

    public ListAdapter(List<Data> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data listItem=listItems.get(position);

        holder.message.setText(listItem.getMessage());
        holder.related_name.setText(listItem.getRelated_person().getTitle());
        holder.created_date.setText(listItem.getCreted_date()+"'de gönderdi.");
        holder.badget_title.setText(listItem.getBadgeData().getTitle());
        //burada id ye göre resimleri atıyorum
        InputStream ims = null;
        try {
            ims = context.getAssets().open("resource/image"+listItem.getBadgeData().getId()+".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        holder.badges_image.setImageDrawable(d);
        holder.ratingBar.setRating(listItem.getPraiseRating());

    }
    //üstteki dataların view holderi
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView related_name;
        TextView created_date;
        TextView badget_title;
        TextView message;
        ImageView badges_image;
        RatingBar ratingBar;
        ImageView profileImage;
        public ViewHolder(View itemView) {
            super(itemView);
            related_name=itemView.findViewById(R.id.related_name);
            created_date=itemView.findViewById(R.id.crated_date);
            badget_title=itemView.findViewById(R.id.badget_title);
            message=itemView.findViewById(R.id.message);
            badges_image=itemView.findViewById(R.id.badges_image);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            profileImage=itemView.findViewById(R.id.profile_image);
        }
    }

    public List<Data> getListItems() {
        return listItems;
    }

    public void setListItems(List<Data> listItems) {
        this.listItems = listItems;
    }


}
