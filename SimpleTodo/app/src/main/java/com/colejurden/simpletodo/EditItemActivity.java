package com.colejurden.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import static android.widget.TextView.BufferType.EDITABLE;
import static com.colejurden.simpletodo.R.id.etModifiedText;

public class EditItemActivity extends AppCompatActivity {
  EditText etModifiedText;
  Spinner spinner2;
  int pos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);
    spinner2 = (Spinner) findViewById(R.id.spinner2);
    pos = getIntent().getIntExtra("pos", 0);
    spinner2.setSelection(getIntent().getIntExtra("priority", 0)-1);
    String text = getIntent().getStringExtra("text");
    setupEditText(text);
    getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
    );
  }

  public void onSave(View v) {
    Intent data = new Intent();
    data.putExtra("text", etModifiedText.getText().toString());
    data.putExtra("pos", pos);
    data.putExtra("priority", Integer.parseInt(spinner2.getSelectedItem().toString()));
    setResult(RESULT_OK, data);
    this.finish();
  }

  private void setupEditText(String text) {
    etModifiedText = (EditText) findViewById(R.id.etModifiedText);
    etModifiedText.setText(text, EDITABLE);
    etModifiedText.setSelection(text.length());

    etModifiedText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
          InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
      }
    });
  }
}
