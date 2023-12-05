package com.example.lab2;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class ShoppingViewHolder extends RecyclerView.ViewHolder {
    public TextView shoppingItem;
    public ShoppingViewHolder(@NonNull View itemView) {
        super(itemView);
        shoppingItem = itemView.findViewById(R.id.shoppingItem);
    }
}
