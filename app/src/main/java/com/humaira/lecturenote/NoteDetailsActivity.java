package com.humaira.lecturenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NoteDetailsActivity extends AppCompatActivity {

    private EditText etCourse, etTopic,etDescription,etDateTime;

    private String existingKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

         etCourse = findViewById(R.id.etCourseId);
         etTopic = findViewById(R.id.etTopic);
         etDescription = findViewById(R.id.etDescription);
         etDateTime = findViewById(R.id.etDateTime);

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
                Intent intent = new Intent(NoteDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent i = getIntent();
        existingKey = i.getStringExtra("Key");
        if(existingKey != null && !existingKey.isEmpty()) {
            initializeForm(existingKey);
        }
    }

    private void save() {
        String course = etCourse.getText().toString();
        String topic = etTopic.getText().toString();
        String dateTime = etDateTime.getText().toString();
        String description = etDescription.getText().toString();

        if(course.isEmpty() || topic.isEmpty() || dateTime.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            NoteDetailsActivity.this.finish();
            return;
        }

        String key = course+"-::-"+dateTime;
        String value = course+"-::-"+topic+"-::-"+dateTime+"-::-"+description;

        Util.getInstance().setKeyValue(this, key, value);
        System.out.println("Data Saved Successfully");

        if (existingKey != null) {
            key = existingKey;
        }

        Toast.makeText(this, "Your Note has been saved successfully", Toast.LENGTH_LONG).show();
        finish();
    }

    private void initializeForm(String eventKey){

        String value = Util.getInstance().getValueByKey(this, eventKey);
        System.out.println("Key: " + eventKey + "; Value: "+value);

        if(value != null) {
            String[] fieldValues = value.split("-::-");
            String course = fieldValues[0];
            String topic = fieldValues[1];
            String dateTime = fieldValues[2];
            String description = fieldValues[3];

            etCourse.setText(course);
            etDateTime.setText(dateTime);
            etTopic.setText(topic);
            etDescription.setText(description);
        }
    }
}