package com.humaira.lecturenote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvNotes;
    private ArrayList<Notes> notes;
    private CustomNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNotes = findViewById(R.id.lvNotes);

        Button btnExit = findViewById(R.id.exitBtn);
        btnExit.setOnClickListener(view -> finish());

        Button btnCreate = findViewById(R.id.createNewBtn);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
                startActivity(intent);
            }
        });

        loadData();
    }

    private void loadData() {
        notes = new ArrayList<>();
        KeyValueDB db = new KeyValueDB(this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }

        while (rows.moveToNext()) {
            String key = rows.getString(0);
            String noteData = rows.getString(1);
            String[] fieldValues = noteData.split("-::-");

            String course = fieldValues[0];
            String topic = fieldValues[1];
            String date = fieldValues[2];
            String description = fieldValues[3];

            Notes n = new Notes(key, course, topic, date, description);
            notes.add(n);
        }
        db.close();
        adapter = new CustomNoteAdapter(this, notes);
        lvNotes.setAdapter(adapter);

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                System.out.println(position);

                Intent i = new Intent(MainActivity.this, NoteDetailsActivity.class);
                i.putExtra("EventKey", notes.get(position).key);
                startActivity(i);
            }
 });


        lvNotes.setOnItemLongClickListener((parent, view, position, id) -> {
            String message = "Do you want to delete this note - " + notes.get(position).course +" "+ notes.get(position).topic +" ?";
            showDialog(message,"Delete Note", notes.get(position).key);
            System.out.println(message);
            return true;
        });

    }

    @Override
    public void onRestart() {
        super.onRestart();
        loadData();
    }

    private void showDialog(String message, String title, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Util.getInstance().deleteByKey(MainActivity.this, key);
                dialog.cancel();
                loadData();
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}