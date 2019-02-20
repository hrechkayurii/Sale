package com.ua.yuriihrechka.sale.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ua.yuriihrechka.sale.Interface.ItemClickListner;
import com.ua.yuriihrechka.sale.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // item card

    public TextView txtProductName;
    public TextView txtProductDescription;
    public TextView txtProductPrice;
    public ImageView mImageView;

    public ItemClickListner mListner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mImageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListner(ItemClickListner listner){
        this.mListner = listner;

    }

    @Override
    public void onClick(View v) {

        mListner.onClick(v, getAdapterPosition(), false);

    }
}
