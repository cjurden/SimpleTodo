package com.colejurden.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {
  static final int EDIT_REQUEST = 2;
  ArrayList<Item> items;
  ArrayAdapter<Item> itemsAdapter;
  ListView lvItems;
  Spinner spinner;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    lvItems = (ListView)findViewById(R.id.lvItems);
    items = new ArrayList<Item>();
    readItems();
    itemsAdapter = new ListAdapter(this, items);
    lvItems.setAdapter(itemsAdapter);
    setupListViewListener();
    setupSpinner();
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      items = new ArrayList<Item>();
      ArrayList<String> texts = new ArrayList<String>(FileUtils.readLines(todoFile));
      for (String text : texts) {
        Item newItem = new Item(text, 3);
        items.add(newItem);
      }
    } catch (IOException e) {
      items = new ArrayList<Item>();
    }
  }

  private void writeItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      FileUtils.writeLines(todoFile, items);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void onAddItem(View v) {
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    int priority = (Integer) spinner.getSelectedItem();
    String etText = etNewItem.getText().toString();
    Item newItem = new Item(etText, priority);
    itemsAdapter.add(newItem);
    etNewItem.setText("");
    this.writeItems();
  }

  private void setupListViewListener() {
    lvItems.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(
              AdapterView<?> adapter, View item, int pos, long id) {
            items.remove(pos);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            return true;
          }
        });
    //TODO: make this a gesture based thing.

    lvItems.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(MainActivity.this, EditItemActivity.class);
            i.putExtra("pos", position);
            i.putExtra("text", items.get(position).text);
            i.putExtra("priority", items.get(position).priority);
            MainActivity.this.startActivityForResult(i, EDIT_REQUEST);
          }
        }
    );
  }

  private void setupSpinner() {
    spinner = (Spinner) findViewById(R.id.spinner);
    spinner.setSelection(0);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    switch (requestCode) {
      case EDIT_REQUEST:
        if (resultCode == RESULT_OK) {
          int pos = data.getIntExtra("pos", 0);
          String text = data.getStringExtra("text");
          int priority = data.getIntExtra("priority", 0);
          Item newItem = new Item(text, priority);
          items.set(pos, newItem);
          writeItems();
      }
    }
  }



}
