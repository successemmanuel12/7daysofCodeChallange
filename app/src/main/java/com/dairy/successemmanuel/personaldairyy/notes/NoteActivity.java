package com.dairy.successemmanuel.personaldairyy.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dairy.successemmanuel.personaldairyy.Database.DatabaseHelper;
import com.dairy.successemmanuel.personaldairyy.MainActivity;
import com.dairy.successemmanuel.personaldairyy.R;

public class NoteActivity extends AppCompatActivity {
    ImageView imageDel, imageSave;
    EditText newNote, noteTitle;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        imageDel = (ImageView) findViewById(R.id.img2);
        imageSave = (ImageView) findViewById(R.id.img1);

        newNote = (EditText)findViewById(R.id.note);
        noteTitle = (EditText)findViewById(R.id.noteTitle);
        db = new DatabaseHelper(this);

        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = newNote.getText().toString();
                String s2 = noteTitle.getText().toString();
                if (s1.equals("")|| s2.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter Title and Note before Saving", Toast.LENGTH_SHORT).show();
                }else {
                    boolean add= db.addNote(s1,s2);
                    if (add){
                        Toast.makeText(getApplicationContext(), "New note Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NoteActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(),"Unable to save note at the moment", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
