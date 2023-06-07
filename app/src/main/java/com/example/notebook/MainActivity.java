package com.example.notebook;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonAddNote;
    private Button buttonDeleteNote;
    private Button buttonEditNote;
    private DatabaseManager databaseManager;
    private List<Note> notesList;
    private ArrayAdapter<Note> noteAdapter;
    private Note selectedNote;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = new DatabaseManager(this);
        notesList = new ArrayList<>();
        noteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);

        buttonAddNote = findViewById(R.id.buttonAddNote);
        editText = findViewById(R.id.noteText);

        ListView notesListView = findViewById(R.id.list);
        notesListView.setAdapter(noteAdapter);

        notesList.addAll(databaseManager.viewAll());
        noteAdapter.notifyDataSetChanged();

        buttonAddNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText noteEditText = findViewById(R.id.noteText);
                String noteText = noteEditText.getText().toString();

                if (!noteText.isEmpty()) {

                    Long id = null;
                    databaseManager.addNote(new Note(id, noteText));
                    noteAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Note added succesfully", Toast.LENGTH_SHORT).show();
                    noteEditText.setText("");

                } else {

                    Toast.makeText(getApplicationContext(), "Please enter a note first", Toast.LENGTH_SHORT).show();
                }
                loadNotes();
            }

        });

        buttonDeleteNote = findViewById(R.id.buttonDeleteNote);
        buttonDeleteNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (selectedNote != null) {
                    databaseManager.deleteNote(selectedNote);
                    selectedNote = null;
                    editText.setText("");
                    loadNotes();
                    Toast.makeText(getApplicationContext(), "Note deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a note to delete.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonEditNote = findViewById(R.id.buttonEditNote);
        buttonEditNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (selectedNote != null) {
                    databaseManager.editNote(Math.toIntExact(selectedNote.getNumber()), editText.getText().toString());
                    selectedNote = null;
                    editText.setText("");
                    loadNotes();
                    Toast.makeText(getApplicationContext(), "Note edited successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a note to edit.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedNote = notesList.get(position);
                editText.setText(selectedNote.getText());
            }
        });

        loadNotes();
    }
    private void loadNotes() {
        notesList.clear();
        notesList.addAll(databaseManager.viewAll());
        noteAdapter.notifyDataSetChanged();
    }

}
