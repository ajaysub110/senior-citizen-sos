package com.example.android.solochana;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

/**
 * Created by ajays on 22-05-2018.
 */

public class ContactsViewHolder extends RecyclerView.ViewHolder{

    private EditText nameEdittext;
    private EditText phoneEdittext;

    public ContactsViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View self) {
        nameEdittext = (EditText) self.findViewById(R.id.name_edittext);
        phoneEdittext = (EditText) self.findViewById(R.id.phone_edittext);
    }

    public void populateViews(Contact contact) {
        nameEdittext.setText(contact.getName());
        phoneEdittext.setText(contact.getPhone());
    }
}
