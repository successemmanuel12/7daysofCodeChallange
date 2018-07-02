package com.dairy.successemmanuel.personaldairyy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dairy.successemmanuel.personaldairyy.Database.DatabaseHelper;
import com.dairy.successemmanuel.personaldairyy.notes.NoteActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        ImageView newNote;
        ListView listView;

        private NoteAdapter noteAdapter = null;
        private Cursor cursor ;
        private DatabaseHelper db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView) findViewById(R.id.all_entries);
            newNote = (ImageView) findViewById(R.id.img3);
            db = new DatabaseHelper(this);

            cursor = db.getList();
            final ArrayList<String> theList = new ArrayList<>();

            startManagingCursor(cursor);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int item = Integer.parseInt(theList.get(position));
                    Intent i = new Intent(view.getContext(), ViewItemActivity.class);
                    startActivityForResult(i,item);
                }
            });


            if (cursor.getCount()==0){
                Toast.makeText(getApplicationContext(),"DATABASE IS EMPTY", Toast.LENGTH_LONG).show();
            }else {
                while (cursor.moveToNext()){
                 theList.add(cursor.getString(1));
                    ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                    listView.setAdapter(listAdapter);
                }
            }
        }catch (Exception e)
        {

            // this is the line of code that sends a real error message to the log
            Log.e("ERROR", "ERROR IN CODE: " + e.toString());

            // this is the line that prints out the location in
            // the code where the error occurred.
            e.printStackTrace();
        }
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NoteActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.action_settings){

        }
        if (id== R.id.action_new_note){
            Intent noteIntent = new Intent(this, NoteActivity.class);
            startActivity(noteIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class NoteAdapter extends CursorAdapter{

        public NoteAdapter(Cursor c) {
            super(MainActivity.this, c);
        }

        public void bindView(View row, Context ctxt, Cursor c) {
            NoteHolder holder=(NoteHolder)row.getTag();
            holder.populateFrom(c, db);
        }
        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.model, parent, false);
            NoteHolder holder=new NoteHolder(row);
            row.setTag(holder);
            return(row);
        }
    }

    static class NoteHolder {
        private TextView note=null;

        NoteHolder(View row) {
            note=(TextView)row.findViewById(R.id.textList);
        }

        void populateFrom(Cursor c, DatabaseHelper r) {
            note.setText(r.getNote(c));
        }
    }
}
