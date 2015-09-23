package com.fernandocejas.example.frodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

  private final View.OnClickListener loadExamplesClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      Intent intent = new Intent(MainActivity.this, SamplesActivity.class);
      startActivity(intent);
    }
  };
  private Button btnLoadExamples;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.mapGUI();
  }

  private void mapGUI() {
    btnLoadExamples = (Button) findViewById(R.id.btnLoadExamples);
    btnLoadExamples.setOnClickListener(loadExamplesClickListener);
  }
}
