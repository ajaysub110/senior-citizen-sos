package com.example.android.solochana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;

public class ListActivity extends AppCompatActivity {

    private TextView titleTextview;
    private Button addButton;
    private RecyclerView recyclerView;
    private RAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bindViews();
        addListeners();
        populateViews();
    }

    private void bindViews() {
        titleTextview = (TextView) findViewById(R.id.title_textview);
        addButton = (Button) findViewById(R.id.add_button);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = new Contact();
                contact.setName("Ajay");
                contact.setPhone("7618739866");
                contact.setLevel(1);

                Realm db = Realm.getDefaultInstance();
                db.beginTransaction();
                db.insertOrUpdate(contact);
                db.commitTransaction();
                db.close();
            }
        });
    }

    void populateViews() {
        titleTextview.setText(getIntent().getStringExtra("title"));
        int lvl = getIntent().getIntExtra("lvl",1);

        adapter = new RAdapter(lvl);
        recyclerView.setAdapter(adapter);
    }
}