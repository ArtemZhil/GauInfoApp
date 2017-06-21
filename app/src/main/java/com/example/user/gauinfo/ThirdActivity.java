package com.example.user.gauinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    ListView listView1;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        listView1 = (ListView) findViewById(R.id.listView3);
        int resID = 0;
        final String resName = getIntent().getStringExtra("BusDirection")+"__"+getIntent().getStringExtra("BusStop").replace(' ', '_').replace('-', '_');
        Toast toast = Toast.makeText(getApplicationContext(),resName, Toast.LENGTH_SHORT);
        //toast.setDuration(Toast.LENGTH_LONG);
        //toast.show();
        resID = getResources().getIdentifier(resName, "array", getPackageName());


        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(resID));
        listView1.setAdapter(mAdapter);
    }

}
