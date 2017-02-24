package com.colejurden.simpletodo;

/**
 * Created by colejurden on 2/17/17.
 */

public class Item {
  public int priority;
  public String text;

  public Item(String text, int priority) {
    this.text = text;
    this.priority = priority;
  }
}
