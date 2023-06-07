package com.example.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context) {

        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(
                "CREATE TABLE NOTES(" +
                        "NUMBER INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "TEXT CHAR(200));" +
                        "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }


    public void addNote(Note note){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("text", note.getText());
        database.insertOrThrow("notes",null, values);
    }

    public void deleteNote(Note note){
        SQLiteDatabase database = getWritableDatabase();
        String[] arguments={String.valueOf(note.getNumber())};
        database.delete("notes", "number=?", arguments);
    }

    public void editNote(int noteId, String newContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("text", newContent);
        String whereClause = "number" + " = " + noteId;
        db.update("notes", values, whereClause, null);

        db.close();
    }

    public List<Note> viewAll(){
        List<Note> notes = new ArrayList<>();
        String[] columns={"number","text"};
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor =database.query("notes",columns,null,null,null,null,null);
        while(cursor.moveToNext()){
            Note note = new Note();
            note.setNumber(cursor.getLong(0));
            note.setText(cursor.getString(1));
            notes.add(note);
        }
        return notes;
    }

}
