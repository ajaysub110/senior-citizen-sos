package com.example.android.solochana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    private Button sosButton;
    private Button lv1Button;
    private Button lv2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        addListeners();
    }

    private void bindViews(){
        sosButton = (Button) findViewById(R.id.sos_button);
        lv1Button = (Button) findViewById(R.id.lv1_button);
        lv2Button = (Button) findViewById(R.id.lv2_button);
    }

    private void smsContacts(int level, RealmResults<Contact> results){
        String messageString = "EMERGENCY! PLEASE CALL IMMEDIATELY!";
        SmsManager smsManager = SmsManager.getDefault();
        for(Contact c: results){
            smsManager.sendTextMessage(c.getPhone(),null,messageString, null,null);
        }
        Toast.makeText(MainActivity.this, "Sent Help message to all Level " + level + " contacts", Toast.LENGTH_SHORT).show();
    }

    private void addListeners(){
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm db = Realm.getDefaultInstance();
                RealmQuery<Contact> lv1query = db.where(Contact.class).equalTo("level",1);

                RealmResults<Contact> lv1results = lv1query.findAll();

                smsContacts(1,lv1results);
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("title", "Call Level 1 Contacts");
                intent.putExtra("level", 1);
                intent.setAction("CallContacts");
                startActivity(intent);
            }
        });
        lv1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("title","Level 1 Contacts");
                intent.putExtra("level", 1);
                intent.setAction("ViewContacts");
                startActivity(intent);
            }
        });

        lv2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("title","Level 2 Contacts");
                intent.putExtra("level", 2);
                intent.setAction("ViewContacts");
                startActivity(intent);
            }
        });
    }
}
