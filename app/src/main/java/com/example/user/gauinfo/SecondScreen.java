package com.example.user.gauinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class SecondScreen extends AppCompatActivity {
ListView listView1;

@Override
  protected void onCreate(Bundle savedInstanceState)
  {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_second_screen);
      listView1 = (ListView) findViewById(R.id.listView2);
      int resID = 0;
      final String resName = getIntent().getStringExtra("BusDirection").replace(' ', '_').replace('-', '_');
      resID = getResources().getIdentifier(resName, "array", getPackageName());


      ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(SecondScreen.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(resID));
      listView1.setAdapter(mAdapter);

      listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent intent = new Intent(SecondScreen.this, ThirdActivity.class);
              intent.putExtra("BusDirection", resName);
              intent.putExtra("BusStop", listView1.getItemAtPosition(i).toString());
              startActivity(intent);
          }
      });
  }
}
