package com.example.android.solochana;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ajays on 22-05-2018.
 */

public class ContactsViewHolder extends RecyclerView.ViewHolder{

    private TextView nameTextview;
    private TextView phoneTextview;

    public ContactsViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View self) {
        nameTextview = (TextView) self.findViewById(R.id.name_textview);
        phoneTextview = (TextView) self.findViewById(R.id.phone_textview);
    }

    public void populateViews(Contact contact) {
        nameTextview.setText(contact.getName());
        phoneTextview.setText(contact.getPhone());
    }
}
