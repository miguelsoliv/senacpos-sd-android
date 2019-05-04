/*
 * MIT License
 *
 * Copyright (c) 2019 Miguel Soares de Oliveira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.miguel.trabalhosd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.miguel.trabalhosd.R;
import com.miguel.trabalhosd.model.Product;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductViewHolder> {
    private List<Product> productsList;
    private Context ctx;
    private ProductItemActionListener actionListener;

    public ProductsListAdapter(Context ctx, List<Product> productsList) {
        this.ctx = ctx;
        this.productsList = productsList;
    }

    public void setActionListener(ProductItemActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        Product product = productsList.get(position);

        holder.textName.setText(product.getName());
        holder.textDesc.setText(product.getDescription());
        holder.textPrice.setText("R$ " + applyDecimalFormat(product.getPrice()));

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK).borderWidthDp(0).cornerRadiusDp(15)
                .oval(false).build();

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/senacpos-sd.appspot.com/o/-LdUxzITDZyOj7XS4ZJI?alt=media&token=69000c34-8619-4ab0-8da8-11bf954f440b").fit().transform(transformation).into(holder.imgPhoto);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/senacpos-sd.appspot.com/o/-LdUxzITDZyOj7XS4ZJI?alt=media&token=69000c34-8619-4ab0-8da8-11bf954f440b").fit().transform(transformation).into(holder.imgPhotoCopy);

        holder.imgPhotoCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null)
                    actionListener.onItemTap(holder.imgPhotoCopy);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView textName, textDesc, textPrice;
        private ImageView imgPhoto, imgPhotoCopy;

        ProductViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textDesc = itemView.findViewById(R.id.textDesc);
            textPrice = itemView.findViewById(R.id.textPrice);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            imgPhotoCopy = itemView.findViewById(R.id.imgPhotoCopy);
        }
    }

    public interface ProductItemActionListener {
        void onItemTap(ImageView imageView);
    }

    private String applyDecimalFormat(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00", symbols);

        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        return "" + decimalFormat.format(value);
    }
}