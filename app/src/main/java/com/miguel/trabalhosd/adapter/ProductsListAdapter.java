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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguel.trabalhosd.R;
import com.miguel.trabalhosd.Utils;
import com.miguel.trabalhosd.model.ImageUrl;
import com.miguel.trabalhosd.model.Product;
import com.squareup.picasso.Picasso;

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
        final Product product = productsList.get(position);

        holder.textName.setText(product.getNome());
        holder.textType.setText(product.getTipo());
        holder.textDesc.setText(product.getDescricao());
        holder.textPrice.setText(Utils.applyDecimalFormat(product.getPreco()));

        DatabaseReference urlsDBReference = FirebaseDatabase.getInstance().getReference("imagensUrl")
                .child(product.getId());
        urlsDBReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot urlSnapshot : dataSnapshot.getChildren()) {
                    Picasso.get().load(urlSnapshot.getValue(ImageUrl.class).getURL()).fit().into(holder.imgPhoto);
                    Picasso.get().load(urlSnapshot.getValue(ImageUrl.class).getURL()).fit().into(holder.imgPhotoCopy);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        holder.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null)
                    actionListener.onItemTap(product, holder.imgPhotoCopy);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView textName, textType, textDesc, textPrice;
        private ImageView imgPhoto, imgPhotoCopy;
        private Button btAdd;

        ProductViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textType = itemView.findViewById(R.id.textType);
            textDesc = itemView.findViewById(R.id.textDesc);
            textPrice = itemView.findViewById(R.id.textPrice);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            imgPhotoCopy = itemView.findViewById(R.id.imgPhotoCopy);
            btAdd = itemView.findViewById(R.id.btAdd);
        }
    }

    public interface ProductItemActionListener {
        void onItemTap(Product prod, ImageView imageView);
    }
}