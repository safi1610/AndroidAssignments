package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.sql.SQLException;
import java.util.ArrayList;



public class ChatWindow extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "ChatWindow";
    ArrayList<String> msgs = new ArrayList<>();

    ChatDatabaseHelper messageDB;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ListView listView = findViewById(R.id.listView);
        EditText msgBox = findViewById(R.id.chatBox);
        Button sendButton = findViewById(R.id.sendButton);
        messageDB = new ChatDatabaseHelper(this);
        database = messageDB.getWritableDatabase();


        ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        Cursor c = database.rawQuery("select * from "+ ChatDatabaseHelper.TABLE_Of_My_ITEMS + ";", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String str = c.getString(c.getColumnIndexOrThrow(ChatDatabaseHelper.KEY_MESSAGE));
            msgs.add(str);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + c.getString(c.getColumnIndexOrThrow(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor Column Count =" + c.getColumnCount());
            c.moveToNext();
        }

        for(int i = 0; i < c.getColumnCount(); i++){
            c.getColumnName(i);
            System.out.println(c.getColumnName(i));
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgs.add(msgBox.getText().toString());

                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, msgBox.getText().toString());
                long insertId = database.insert(ChatDatabaseHelper.TABLE_Of_My_ITEMS, null, values);

                Cursor c2 = database.query(ChatDatabaseHelper.TABLE_Of_My_ITEMS, null, ChatDatabaseHelper.KEY_ID + "=" + insertId, null, null, null, null);

                c2.moveToFirst();

                c2.close();

                messageAdapter.notifyDataSetChanged();
                msgBox.setText("");

            }
        });
    }



    @Override
    protected void onDestroy() {
        messageDB.close();
        super.onDestroy();
    }

    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return msgs.size();
        }

        public String getItem(int position){
            return(msgs.get(position));
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            if(position % 2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);

            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById((R.id.msgText));

            message.setText(getItem(position));

            return result;

        }


    }
}