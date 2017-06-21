
package com.example.user.gauinfo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.ListView;
import android.widget.AdapterView;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import android.content.SharedPreferences;



public class MainActivity extends AppCompatActivity {

    TabHost tabHost;
    ListView listView;
    Button CourseAddButton;
    Button CourseSearchButton;
    TextView CourseResultText;
    ListView CourseListView;
    EditText editText1;
    Button AddButton1;
    Button CourseDelButton;
    TextView TextView1;

    String ItemToDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CourseAddButton = (Button) findViewById(R.id.button1);
        CourseDelButton = (Button) findViewById(R.id.button3);
        CourseSearchButton = (Button) findViewById(R.id.button);
        CourseResultText =(TextView) findViewById(R.id.CoursesTextView);
        CourseListView = (ListView) findViewById(R.id.listView11);

        UpdateCourses();

        CourseAddButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String str = CourseResultText.getText().toString();
                try
                {
                    AddCoursesItem(str);
                }
                catch (IOException ie) {}
                UpdateCourses();
                CourseResultText.setText("");
            }
        });

        final String strArr[] = getResources().getStringArray(R.array.CoursesTable);
        //Для ввода предметов
        TextView1 =(TextView) findViewById(R.id.CoursesTextView);
        editText1 = (EditText) findViewById(R.id.edittext);
        AddButton1= (Button) findViewById(R.id.button);

        AddButton1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = editText1.getText().toString();
                //str = "";
                String tempStr[];
                String res = "Not Found";
                for (int i=0; i < strArr.length; i++) {
                    tempStr = strArr[i].split(" ,");
                    if (tempStr[0].equalsIgnoreCase(str)) {
                        res = strArr[i];
                        break;

                    }
                }
                TextView1.setText(res);

            }
        });

        CourseDelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DelCoursesItem(ItemToDelete);
                UpdateCourses();
                CourseDelButton.setText("DELETE");
            }
        });

        CourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemToDelete = CourseListView.getItemAtPosition(i).toString();
                CourseDelButton.setText("Delete "+ItemToDelete);
            }
        });


        //Список автобусов
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Busses));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SecondScreen.class);
                intent.putExtra("BusDirection", listView.getItemAtPosition(i).toString());
                startActivity(intent);

            }
        });
        listView.setAdapter(mAdapter);
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Busses");
        spec.setContent(R.id.Busses);
        spec.setIndicator("Busses");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Courses");
        spec.setContent(R.id.Courses);
        spec.setIndicator("Courses");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("About");
        spec.setContent(R.id.About);
        spec.setIndicator("About");
        host.addTab(spec);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        return true;
    }

    public void UpdateCourses()
    {
        Set<String> strSet = new HashSet<>();
        try {
            FileInputStream fis = openFileInput("ChosenCourses");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(fis));
            String Line;
            while ((Line= stdIn.readLine()) != null) {
                strSet.add(Line);
            }
            fis.close();
        }
        catch (IOException ie) {}
        ArrayAdapter<String> adapter;
        // Конвертируем HashSet в массив
        String[] myArray = {};
        myArray = strSet.toArray(new String[strSet.size()]);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, myArray);
        CourseListView.setAdapter(adapter);
    }

    public void AddCoursesItem(String str) throws IOException
    {
        if (str.equalsIgnoreCase("Not Found") || str.equalsIgnoreCase("") ) {return ;}
        try {
            FileOutputStream fos = openFileOutput("ChosenCourses",MODE_APPEND);
            byte[] buffer = (str+"\n").getBytes();
            fos.write(buffer);

            fos.flush();
            fos.close();
        } catch (IOException ie) {}
    }

    public void DelCoursesItem(String str)
    {
        Set<String> strSet = new HashSet<>();
        try {
            FileInputStream fis = openFileInput("ChosenCourses");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(fis));
            String Line;
            while ((Line= stdIn.readLine()) != null) {
                strSet.add(Line);
            }
            fis.close();
        }
        catch (IOException ie) {}


        Iterator<String> itr = strSet.iterator();  // list is a Set<String>!
        while (itr.hasNext())
        {
            String s = itr.next();
            if (s.equalsIgnoreCase(str))
            {
                itr.remove();
                break;
            }
        }

        try {
            FileOutputStream fos = openFileOutput("ChosenCourses",MODE_PRIVATE);
            itr = strSet.iterator();
            while (itr.hasNext())
            {
                String s = itr.next();
                byte[] buffer = (s+"\n").getBytes();
                fos.write(buffer);
            }

            fos.flush();
            fos.close();
        } catch (IOException ie) {}
    }
    //return super.onOptionsItemSelected(item);
}



/*
 public void UpdateCourses()
    {
        SharedPreferences SharedPrefs = getSharedPreferences("GAU_INFO",MODE_PRIVATE);;
        Set<String> strSet = new HashSet<>();
        strSet = SharedPrefs.getStringSet("ChosenCourses",strSet);
        ArrayAdapter<String> adapter;
        // Конвертируем HashSet в массив
        String[] myArray = {};
        myArray = strSet.toArray(new String[strSet.size()]);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, myArray);
        CourseListView.setAdapter(adapter);
    }

    public void AddCoursesItem(String str)
    {
        if (str.equalsIgnoreCase("Not Found")) {return ;};
        SharedPreferences SharedPrefs = getSharedPreferences("GAU_INFO",MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPrefs.edit();
        Set<String> strSet = new HashSet<>();
        strSet = SharedPrefs.getStringSet("ChosenCourses",strSet);
        strSet.add(str);
        editor.putStringSet("ChosenCourses",strSet);
        editor.commit();
        editor.apply();
    }

    public void DelCoursesItem(String str)
    {
        SharedPreferences SharedPrefs = getSharedPreferences("GAU_INFO",MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPrefs.edit();
        Set<String> strSet = new HashSet<>();
        strSet = SharedPrefs.getStringSet("ChosenCourses",strSet);
        Iterator<String> itr = strSet.iterator();  // list is a Set<String>!
        while (itr.hasNext())
        {
            String s = itr.next();
            if (s.equalsIgnoreCase(str))
            {
                itr.remove();
                break;
            }
        }
        editor.putStringSet("ChosenCourses",strSet);
        editor.commit();
        editor.apply();
    }





 */