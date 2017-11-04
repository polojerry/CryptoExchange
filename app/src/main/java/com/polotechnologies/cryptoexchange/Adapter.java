package com.polotechnologies.cryptoexchange;

/**
 * Created by Polo on 04/11/2017.
 */

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public Adapter(List<PriceItem> priceItems, Context context) {
        this.priceItems = priceItems;
        this.context = context;
    }

    private List<PriceItem> priceItems;
    private Context context;

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Adapter.ViewHolder holder, int position) {

        final PriceItem priceItem = priceItems.get(position);

        holder.btcPrice.setText(Double.toString(priceItem.getBTC()));
        holder.ethPrice.setText(Double.toString(priceItem.getETH()));

        Picasso.with(context)
                .load(priceItem.getImageCurrencyLoad())
                .into(holder.imageView);

        /*holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Calculation.class);
                intent.putExtra("btc_price",priceItem.getBTC());
                intent.putExtra("eth_price",priceItem.getETH());
                context.startActivity(intent);
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return priceItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView btcPrice;
        public TextView ethPrice;
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        public ViewHolder(final View itemView) {
            super(itemView);

            btcPrice=(TextView)itemView.findViewById(R.id.btcPrice);
            ethPrice=(TextView)itemView.findViewById(R.id.ethPrice);
            imageView= (ImageView)itemView.findViewById(R.id.imageCurrency);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.relativeLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),Calculation.class);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}

