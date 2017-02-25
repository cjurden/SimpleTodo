package com.colejurden.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by colejurden on 2/17/17.
 */

public class ListAdapter extends ArrayAdapter<Item> {
  private Context context;
  private ArrayList<Item> items;

  public ListAdapter(Context context, ArrayList<Item> items) {
    super(context, 0, items);
    this.items = items;
    this.context = context;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    Item item = items.get(position);

    if (view==null) {
      view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    TextView priority = (TextView) view.findViewById(R.id.priority);
    TextView text = (TextView) view.findViewById(R.id.txt);

    priority.setText(Integer.toString(item.priority));
    text.setText(item.text);

    switch(Integer.toString(item.priority)) {
      case "1":
        priority.setTextColor(Color.rgb(255,51,51));
      case "2":
        priority.setTextColor(Color.rgb(255,153,51));
      case "3":
        priority.setTextColor(Color.rgb(0,204,0));
    }

    return view;
  }
}
