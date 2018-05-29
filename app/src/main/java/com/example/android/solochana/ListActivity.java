package com.example.android.solochana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        Intent intent = getIntent();
        addListeners(intent);
        populateViews(intent);
    }

    private void bindViews() {
        titleTextview = (TextView) findViewById(R.id.title_textview);
        addButton = (Button) findViewById(R.id.add_button);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
    }

    void populateViews(final Intent intent) {
        titleTextview.setText(intent.getStringExtra("title"));

        adapter = new RAdapter(ListActivity.this,intent);
        recyclerView.setAdapter(adapter);
    }
}
