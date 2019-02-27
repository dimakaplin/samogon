package ru.dimakaplin143.samogon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.dimakaplin143.samogon.R;

import java.util.Map;
import java.util.TreeMap;

public class NoteList extends AppCompatActivity {
    Map<Integer, View> notes;
    int counter = 1;

    private final View.OnClickListener addNewNote = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final GridLayout linear = (GridLayout) findViewById(R.id.base);
            final View view = getLayoutInflater().inflate(R.layout.note_list_element, null);
            Button deleteField = (Button) view.findViewById(R.id.button2);
            EditText text = (EditText) view.findViewById(R.id.editText);
            view.setId(counter);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(counter);
            view.setLayoutParams(params);
            notes.put(counter, view);
            linear.addView(view);
            counter++;
            System.out.println("created");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        this.setTitle("Заметки");
        notes = new TreeMap<>();
        Button calcButton  = (Button) findViewById(R.id.add_new_note);
        calcButton.setOnClickListener(addNewNote);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.back_to_main:
                Intent intent = new Intent(NoteList.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
