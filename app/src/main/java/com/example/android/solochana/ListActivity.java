package com.example.android.solochana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ListActivity extends AppCompatActivity {

    private TextView titleTextview;
    private Button addButton;
    private RecyclerView recyclerView;
    private RAdapter adapter;
    public Button level2Button;
    public Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bindViews();
        Intent intent = getIntent();
        addListeners(intent);
        populateViews(intent);
    }

    private void bindViews() {
        titleTextview = (TextView) findViewById(R.id.title_textview);
        addButton = (Button) findViewById(R.id.add_button);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        level2Button = (Button) findViewById(R.id.level2_button);
        homeButton = (Button) findViewById(R.id.home_button);
    }

    private void addListeners(final Intent intent) {
        if(intent.getAction().equals("ViewContacts")){
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("level",intent.getIntExtra("level",1));
                    InputDialogFragment inputDialogFragment = new InputDialogFragment();
                    inputDialogFragment.setArguments(bundle);
                    inputDialogFragment.show(getFragmentManager(),"add_contact");
                }
            });
        }
        else {
            addButton.setVisibility(View.GONE);
        }
        level2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm db = Realm.getDefaultInstance();
                RealmQuery<Contact> lv1query = db.where(Contact.class).equalTo("level",2);

                RealmResults<Contact> lv1results = lv1query.findAll();

                smsContacts(2,lv1results);
                Intent intent = new Intent(ListActivity.this, ListActivity.class);
                intent.putExtra("title", "Call Level 2 Contacts");
                intent.putExtra("level", 2);
                intent.setAction("CallContacts");
                startActivity(intent);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void smsContacts(int level, RealmResults<Contact> results){
        String messageString = "EMERGENCY! PLEASE CALL IMMEDIATELY!";
        SmsManager smsManager = SmsManager.getDefault();
        for(Contact c: results){
            smsManager.sendTextMessage(c.getPhone(),null,messageString, null,null);
        }
        Toast.makeText(ListActivity.this, "Sent Help message to all Level " + level + " contacts", Toast.LENGTH_SHORT).show();
    }

    void populateViews(final Intent intent) {
        titleTextview.setText(intent.getStringExtra("title"));
        adapter = new RAdapter(ListActivity.this,intent);
        recyclerView.setAdapter(adapter);
    }
}
