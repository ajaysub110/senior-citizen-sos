package com.example.android.solochana;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
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

    private void callAllContacts(int level, RealmResults<Contact> results){
        for(Contact c: results){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+c.getPhone()));
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
            }
            Toast.makeText(MainActivity.this,"Calling " + c.getName(),Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    private void addListeners(){
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsMessage = "EMERGENCY! PLEASE CALL IMMEDIATELY.";
                Realm db = Realm.getDefaultInstance();
                RealmQuery<Contact> lv1query = db.where(Contact.class).equalTo("level",1);
                RealmQuery<Contact> lv2query = db.where(Contact.class).equalTo("level",2);

                RealmResults<Contact> lv1results = lv1query.findAll();
                RealmResults<Contact> lv2results = lv2query.findAll();

                callAllContacts(1,lv1results);
            }
        });
        lv1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("title","Level 1 Contacts");
                intent.putExtra("lvl", 1);
                startActivity(intent);
            }
        });

        lv2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("title","Level 2 Contacts");
                intent.putExtra("lvl", 2);
                startActivity(intent);
            }
        });
    }


}
