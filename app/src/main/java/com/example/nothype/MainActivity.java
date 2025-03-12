package com.example.nothype;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mainTextView;
    private EditText ImInEdit;
    private Button ok_btn, cnc_btn, delete_btn, addButton;
    private ListView mainListView;
    private ArrayAdapter<String> mArrayAdapter;
    private Set<String> mNameSet = new TreeSet<>(); // Тип для сортировки и уборки дубликатов
    private ArrayList<String> mNameList = new ArrayList<>();
    private String selectedItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTextView = findViewById(R.id.textview_first);
        mainTextView.setText("Kakoi-to prikol");
        ImInEdit = findViewById(R.id.main_editor);

        mainListView = findViewById(R.id.main_listview);
        delete_btn = findViewById(R.id.delete_btn);

        ok_btn = findViewById(R.id.ok_btn);
        cnc_btn = findViewById(R.id.cnc_btn);

        addButton = findViewById(R.id.button_main);

        delete_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
        cnc_btn.setOnClickListener(this);
        addButton.setOnClickListener(this);

        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mNameList);
        mainListView.setAdapter(mArrayAdapter);
        mainListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ok_btn) {
            mainTextView.setText("Нажата кнопка ОК");
            Toast.makeText(getApplicationContext(), "Нажата кнопка ОК", Toast.LENGTH_LONG).show();
        } else if (id == R.id.cnc_btn) {
            mainTextView.setText("Нажата кнопка Cancel");
            Toast.makeText(getApplicationContext(), "Нажата кнопка Cancel", Toast.LENGTH_LONG).show();
        } else if (id == R.id.delete_btn && selectedItem != null) {
            mNameSet.remove(selectedItem);
            updateListView();
            Toast.makeText(getApplicationContext(), "Удалено: " + selectedItem, Toast.LENGTH_SHORT).show();
            selectedItem = null;
        } else if (id == R.id.button_main) {
            String text = ImInEdit.getText().toString().trim();
            if (!text.isEmpty() && mNameSet.add(text)) {
                updateListView();
                Toast.makeText(getApplicationContext(), "Добавлен: " + text, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Уже есть такая шляпа", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedItem = mArrayAdapter.getItem(position);
        Toast.makeText(this, "Выбрано: " + selectedItem, Toast.LENGTH_SHORT).show();
    }

    private void updateListView() {
        mNameList.clear();
        mNameList.addAll(mNameSet);
        mArrayAdapter.notifyDataSetChanged();
    }
}