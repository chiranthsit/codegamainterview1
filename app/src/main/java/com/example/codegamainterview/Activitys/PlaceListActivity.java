package com.example.codegamainterview.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codegamainterview.Database.SqliteDB;
import com.example.codegamainterview.R;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlaceListActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    Cursor cursor;
    SqliteDB sqliteDB;
    Map<String, String> placeslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        Initilize();
    }

    private void Initilize() {

        sqliteDB=new SqliteDB(PlaceListActivity.this);
        placeslist=(new LinkedHashMap<>());

        listView=(ListView)findViewById(R.id.simpleListView);
        textView=(TextView)findViewById(R.id.textView);

        cursor=sqliteDB.getAllData();
        if(cursor.getCount()==0){
            Toast.makeText(PlaceListActivity.this, "No Data", Toast.LENGTH_LONG).show();
            return;

        }else{
            Toast.makeText(PlaceListActivity.this, "Data not empty", Toast.LENGTH_LONG).show();
            while (cursor.moveToNext()){
                placeslist.put(cursor.getString(0),cursor.getString(1));
            }
        }

        //list adaptor
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.places,R.id.textView,placeslist.keySet().toArray());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String test= (String) placeslist.values().toArray()[i];
                System.out.println("Info:"+test);
                Intent intent = new Intent(PlaceListActivity.this, MapsActivity.class);
                intent.putExtra("lat/log",test);
                startActivity(intent);
            }
        });
    }
}