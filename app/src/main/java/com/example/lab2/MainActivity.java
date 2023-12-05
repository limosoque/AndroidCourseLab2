package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView shoppingRecyclerView;
    private ShoppingAdapter shoppingAdapter;
    private final List<String> shoppingList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingRecyclerView = findViewById(R.id.shoppingRecyclerView);
        shoppingAdapter = new ShoppingAdapter(shoppingList);
        shoppingRecyclerView.setAdapter(shoppingAdapter);
        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // бинд кнопки показа справки
        findViewById(R.id.infoButton).setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),
                    "Нажатие на элемент - изменение\nДлительное нажатие - удаление",
                    Toast.LENGTH_SHORT).show();
        });

        // бинд кнопки добавления нового элемента
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });

        // бинд обработчика клика на элемент для редактирования
        shoppingAdapter.setOnItemClickListener(new ShoppingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showEditItemDialog(position);
            }
        });

        // бинд обработчика клика на элемент для удаления
        shoppingAdapter.setOnItemLongClickListener(new ShoppingAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                shoppingList.remove(position);
                shoppingAdapter.notifyItemRemoved(position);
                return true;
            }
        });
    }

    private void showAddItemDialog() {
        final EditText editText = new EditText(this);
        //Создание окна с вводом нового продукта
        new AlertDialog.Builder(this)
                .setTitle("Добавить продукт")
                .setView(editText)
                //Создание лисенера-лямбда-выражение, который добавляет новый элемент
                .setPositiveButton("Добавить", (dialog, which) -> {
                    String newItem = editText.getText().toString();
                    //не добавляем пустую строку
                    if(!newItem.equals("")) {
                        shoppingList.add(newItem);
                        //Сообщаем адаптеру, что элемент добавлен, и он перерисовывает эту позицию на экране
                        shoppingAdapter.notifyItemInserted(shoppingList.size() - 1);
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void showEditItemDialog(final int position) {
        final EditText editText = new EditText(this);
        //Показываем старое значение из листа
        editText.setText(shoppingList.get(position));
        //Создание окна с редактированием продукта
        new AlertDialog.Builder(this)
                .setTitle("Редактировать продукт")
                .setView(editText)
                //Создание лисенера-лямбда-выражение, который изменяет элемент
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    String updatedItem = editText.getText().toString();
                    //если пользователь редактирует строку до пустой, удаляем ее
                    if(updatedItem.equals("")){
                        shoppingList.remove(position);
                        shoppingAdapter.notifyItemRemoved(position);
                    }
                    else {
                        shoppingList.set(position, updatedItem);
                        //Сообщаем адаптеру, что элемент изменен, и он перерисовывает эту позицию на экране
                        shoppingAdapter.notifyItemChanged(position);
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}

