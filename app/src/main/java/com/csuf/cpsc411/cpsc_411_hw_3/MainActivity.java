package com.csuf.cpsc411.cpsc_411_hw_3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    protected Menu detailMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        File dbFile = this.getDatabasePath("hw3.db");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
        db.execSQL("DROP TABLE IF EXISTS Student");
        db.execSQL("DROP TABLE IF EXISTS CourseEnrollment");
        db.execSQL("CREATE TABLE IF NOT EXISTS Student (FirstName TEXT, LastName TEXT, CWID INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS CourseEnrollment (ID INTEGER, Title TEXT)");
        // Sample Data
        db.execSQL("DELETE FROM Student WHERE CWID=?", new String[]{"758669998"});
        db.execSQL("INSERT INTO Student VALUES ('Jake', 'Shen', 758669998)");
        db.execSQL("DELETE FROM CourseEnrollment WHERE ID=?", new String[]{"411"});
        db.execSQL("INSERT INTO CourseEnrollment VALUES (411, 'CPSC 411')");
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu_screen, menu);
        menu.findItem(R.id.action_edit).setVisible(true);
        menu.findItem(R.id.action_done).setVisible(false);
        detailMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            EditText editView = findViewById(R.id.s_first_name_id);
            editView.setEnabled(true);
            editView = findViewById(R.id.s_last_name_id);
            editView.setEnabled(true);
            editView = findViewById(R.id.s_cwid);
            editView.setEnabled(true);
            editView = findViewById(R.id.class_id);
            editView.setEnabled(true);
            editView = findViewById(R.id.class_name_id);
            editView.setEnabled(true);
            //
            item.setVisible(false);
            detailMenu.findItem(R.id.action_done).setVisible(true);
        } else if (item.getItemId() == R.id.action_done) {
            EditText editView = findViewById(R.id.s_first_name_id);
            String temp = editView.getText().toString();
            editView.setEnabled(false);
            editView = findViewById(R.id.s_last_name_id);
            String temp2 = editView.getText().toString();
            editView.setEnabled(false);
            editView = findViewById(R.id.s_cwid);
            String temp3 = editView.getText().toString();
            editView.setEnabled(false);
            File dbFile = this.getDatabasePath("hw3.db");
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
            ContentValues vals = new ContentValues();
            vals.put("FirstName", temp);
            vals.put("LastName", temp2);
            vals.put("CWID", temp3);
            db.insert("Student", null, vals);
            Cursor c = db.query("Student", null, null, null, null, null, null);
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    String fName = c.getString(c.getColumnIndex("FirstName"));
                    String lName = c.getString(c.getColumnIndex("LastName"));
                    Integer cwid = c.getInt(c.getColumnIndex("CWID"));
                    Log.d("Database Log", "First Name: " + fName + "; " + "Last Name: " + lName + "; " + "CWID: " + cwid.toString());
                }
            }
            editView = findViewById(R.id.class_id);
            temp = editView.getText().toString();
            editView.setEnabled(false);
            editView = findViewById(R.id.class_name_id);
            temp2 = editView.getText().toString();
            editView.setEnabled(false);
            vals = new ContentValues();
            vals.put("ID", temp);
            vals.put("Title", temp2);
            db.insert("CourseEnrollment", null, vals);
            c = db.query("CourseEnrollment", null, null, null, null, null, null);
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    Integer id = c.getInt(c.getColumnIndex("ID"));
                    String title = c.getString(c.getColumnIndex("Title"));
                    Log.d("Database Log", "ID: " + id.toString() + "; " + "Title: " + title);
                }
            }
            db.close();
            item.setVisible(false);
            detailMenu.findItem(R.id.action_edit).setVisible(true);
        }
        return super.onOptionsItemSelected(item);
    }
}

