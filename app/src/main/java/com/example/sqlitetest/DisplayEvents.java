package com.example.sqlitetest;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dblibrary.DBHelper;

public class DisplayEvents extends AppCompatActivity {
    private DBHelper mydb ;

    TextView eventTimeText;
    TextView hostIDText;
    TextView userIDText;
    TextView locationNbrText;
    TextView routeNbrText;
    TextView dayText;
    TextView loggerText;
    TextView eventNbrText;
    TextView addtlDescText;
    TextView addtlNbrText;

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        eventTimeText = (TextView) findViewById(R.id.et_EventTime);;
        hostIDText = (TextView) findViewById(R.id.et_HostID);
        userIDText = (TextView) findViewById(R.id.et_UserID);
        locationNbrText = (TextView) findViewById(R.id.et_LocationNbr);
        routeNbrText = (TextView) findViewById(R.id.et_RouteNbr);
        dayText = (TextView) findViewById(R.id.et_Day);
        loggerText = (TextView) findViewById(R.id.et_Logger);
        eventNbrText = (TextView) findViewById(R.id.et_EventNbr);
        addtlDescText = (TextView) findViewById(R.id.et_AddtlDesc);
        addtlNbrText = (TextView) findViewById(R.id.et_AddtlNbr);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int id = extras.getInt("id");
            if(id>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(id);
                id_To_Update = id;
                rs.moveToFirst();

                int eventTimeIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_EVENTTIME);
                int hostIDIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_HOSTID);
                int userIDIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_USERID);
                int locationNbrIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_LOCATIONNBR);
                int routeNbrIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_ROUTENBR);
                int dayIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_DAY);
                int loggerIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_LOGGER);
                int eventNbrIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_EVENTNBR);
                int addtlDescIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_ADDTLDESC);
                int addtlNbrIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_ADDTLNBR);

                String eventTime = "";
                String hostID = "";
                String userID = "";
                String locationNbr = "";
                String routeNbr = "";
                String day = "";
                String logger = "";
                String eventNbr = "";
                String addtlDesc = "";
                String addtlNbr = "";
                if(eventTimeIndex >= 0) {
                    eventTime = rs.getString(eventTimeIndex);
                }
                if(hostIDIndex >= 0) {
                    hostID = rs.getString(hostIDIndex);
                }
                if(userIDIndex >= 0) {
                    userID = rs.getString(userIDIndex);
                }
                if(locationNbrIndex >= 0) {
                    locationNbr = rs.getString(locationNbrIndex);
                }
                if(routeNbrIndex >= 0) {
                    routeNbr = rs.getString(routeNbrIndex);
                }
                if(dayIndex >= 0) {
                    day = rs.getString(dayIndex);
                }
                if(loggerIndex >= 0) {
                    logger = rs.getString(loggerIndex);
                }
                if(eventNbrIndex >= 0) {
                    eventNbr = rs.getString(eventNbrIndex);
                }
                if(addtlDescIndex >= 0) {
                    addtlDesc = rs.getString(addtlDescIndex);
                }
                if(addtlNbrIndex >= 0) {
                    addtlNbr = rs.getString(addtlNbrIndex);
                }

                if (!rs.isClosed())  {
                    rs.close();
                }

                Button b = (Button)findViewById(R.id.btn_Save);
                b.setVisibility(View.INVISIBLE);

                eventTimeText.setText((CharSequence)eventTime);
                eventTimeText.setFocusable(false);
                eventTimeText.setClickable(false);

                hostIDText.setText((CharSequence)hostID);
                hostIDText.setFocusable(false);
                hostIDText.setClickable(false);

                userIDText.setText((CharSequence)userID);
                userIDText.setFocusable(false);
                userIDText.setClickable(false);

                locationNbrText.setText((CharSequence)locationNbr);
                locationNbrText.setFocusable(false);
                locationNbrText.setClickable(false);

                routeNbrText.setText((CharSequence)routeNbr);
                routeNbrText.setFocusable(false);
                routeNbrText.setClickable(false);

                dayText.setText((CharSequence)day);
                dayText.setFocusable(false);
                dayText.setClickable(false);

                loggerText.setText((CharSequence)logger);
                loggerText.setFocusable(false);
                loggerText.setClickable(false);

                eventNbrText.setText((CharSequence)eventNbr);
                eventNbrText.setFocusable(false);
                eventNbrText.setClickable(false);

                addtlDescText.setText((CharSequence)addtlDesc);
                addtlDescText.setFocusable(false);
                addtlDescText.setClickable(false);

                addtlNbrText.setText((CharSequence)addtlNbr);
                addtlNbrText.setFocusable(false);
                addtlNbrText.setClickable(false);

                if(extras.getBoolean("isEditable")) {
                    makeEditable();
                }
            }
        }
    }

    public void makeEditable() {
        Button b = (Button)findViewById(R.id.btn_Save);
        b.setVisibility(View.VISIBLE);
        eventTimeText.setEnabled(true);
        eventTimeText.setFocusableInTouchMode(true);
        eventTimeText.setClickable(true);

        hostIDText.setEnabled(true);
        hostIDText.setFocusableInTouchMode(true);
        hostIDText.setClickable(true);

        userIDText.setEnabled(true);
        userIDText.setFocusableInTouchMode(true);
        userIDText.setClickable(true);

        locationNbrText.setEnabled(true);
        locationNbrText.setFocusableInTouchMode(true);
        locationNbrText.setClickable(true);

        routeNbrText.setEnabled(true);
        routeNbrText.setFocusableInTouchMode(true);
        routeNbrText.setClickable(true);

        dayText.setEnabled(true);
        dayText.setFocusableInTouchMode(true);
        dayText.setClickable(true);

        loggerText.setEnabled(true);
        loggerText.setFocusableInTouchMode(true);
        loggerText.setClickable(true);

        eventNbrText.setEnabled(true);
        eventNbrText.setFocusableInTouchMode(true);
        eventNbrText.setClickable(true);

        addtlDescText.setEnabled(true);
        addtlDescText.setFocusableInTouchMode(true);
        addtlDescText.setClickable(true);

        addtlNbrText.setEnabled(true);
        addtlNbrText.setFocusableInTouchMode(true);
        addtlNbrText.setClickable(true);
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int id = extras.getInt("id");
            if(id>0){
                if(mydb.updateEvents(id_To_Update,eventTimeText.getText().toString(),
                 hostIDText.getText().toString(),
                 userIDText.getText().toString(),
                 locationNbrText.getText().toString(),
                 routeNbrText.getText().toString(),
                 dayText.getText().toString(),
                 loggerText.getText().toString(),
                 eventNbrText.getText().toString(),
                 addtlDescText.getText().toString(),
                 addtlNbrText.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertEvent(eventTimeText.getText().toString(),
                        hostIDText.getText().toString(),
                        userIDText.getText().toString(),
                        locationNbrText.getText().toString(),
                        routeNbrText.getText().toString(),
                        dayText.getText().toString(),
                        loggerText.getText().toString(),
                        eventNbrText.getText().toString(),
                        addtlDescText.getText().toString(),
                        addtlNbrText.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}