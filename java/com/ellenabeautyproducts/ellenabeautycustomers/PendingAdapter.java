package com.ellenabeautyproducts.ellenabeautycustomers;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KELVIN on 28/05/2017.
 */
public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {


    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;


    private List<ListItem> listItems;
    private Context context;

    public PendingAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);

        holder.textViewQuantity.setText(listItem.getQuantity());
        holder.textViewCustomer.setText(listItem.getCustomer());
        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDate.setText(listItem.getDate());
        Picasso.with(context).load(listItem.getImageUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHead;
        public TextView textViewCustomer;
        public TextView textViewDate;
        public TextView textViewQuantity;
        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView)itemView.findViewById(R.id.textViewRecyclerProduct);
            textViewCustomer = (TextView)itemView.findViewById(R.id.textViewRecyclerCustomer);
            textViewDate = (TextView)itemView.findViewById(R.id.textViewRecyclerDate);
            textViewQuantity = (TextView)itemView.findViewById(R.id.textViewRecyclerQuantity);
            imageView =(ImageView)itemView.findViewById(R.id.imageViewRecycler);
        }
    }

    public static void setFilter(List<ListItem> listItems) {
        listItems = new ArrayList<>();
        listItems.addAll(listItems);

    }
}
