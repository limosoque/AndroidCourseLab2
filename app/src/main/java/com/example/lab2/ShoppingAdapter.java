package com.example.lab2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingViewHolder> {
    private final List<String> shoppingList;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }
    public ShoppingAdapter(List<String> dataList) {
        this.shoppingList = dataList;
    }
    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //создание объекта, размеченного в shopping_item
        View shoppingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item, parent, false);
        return new ShoppingViewHolder(shoppingView);
    }
    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        String data = shoppingList.get(position);
        //Обновление данных recyclerView
        holder.shoppingItem.setText(data);
        // бинд обработчика клика на элемент
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(holder.getBindingAdapterPosition());
            }
        });
        // бинд обработчика долгого клика на элемент
        holder.itemView.setOnLongClickListener(v -> {
            if (itemLongClickListener != null) {
                return itemLongClickListener.onItemLongClick(holder.getBindingAdapterPosition());
            }
            return false;
        });
    }
    @Override
    public int getItemCount() {
        return shoppingList.size();
    }
}
