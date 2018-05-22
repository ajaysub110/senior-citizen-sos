package com.example.android.solochana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


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

    private void addListeners(){
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
