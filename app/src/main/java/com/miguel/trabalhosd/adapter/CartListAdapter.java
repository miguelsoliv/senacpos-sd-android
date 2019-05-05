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
import android.widget.TextView;

import com.miguel.trabalhosd.R;
import com.miguel.trabalhosd.Utils;
import com.miguel.trabalhosd.activity.MainActivity;
import com.miguel.trabalhosd.model.Product;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {
    private Context ctx;

    public CartListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(ctx).inflate(R.layout.list_item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = MainActivity.getCartList().get(position);

        holder.textName.setText(product.getNome());
        holder.textType.setText(product.getTipo());
        holder.textQuant.setText(product.getQuant() + " x");
        holder.textPrice.setText(Utils.applyDecimalFormat(product.getPreco()));
    }

    @Override
    public int getItemCount() {
        return MainActivity.getCartList().size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView textName, textType, textQuant, textPrice;

        CartViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textType = itemView.findViewById(R.id.textType);
            textQuant = itemView.findViewById(R.id.textQuant);
            textPrice = itemView.findViewById(R.id.textPrice);
        }
    }
}