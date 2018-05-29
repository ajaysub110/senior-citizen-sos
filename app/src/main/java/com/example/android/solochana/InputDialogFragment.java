package com.example.android.solochana;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;

/**
 * Created by ajays on 23-05-2018.
 */

public class InputDialogFragment extends DialogFragment{

    EditText nameEdittext;
    EditText phoneEdittext;
    int lvl;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_input,null);

        nameEdittext = (EditText) v.findViewById(R.id.name_edittext);
        phoneEdittext = (EditText) v.findViewById(R.id.phone_edittext);


        builder.setView(v)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Contact contact = new Contact();
                        contact.setName(nameEdittext.getText().toString());
                        contact.setPhone(phoneEdittext.getText().toString());
                        contact.setLevel(getArguments().getInt("level"));

                        Realm db = Realm.getDefaultInstance();
                        db.beginTransaction();
                        db.insertOrUpdate(contact);
                        db.commitTransaction();
                        db.close();

                        InputDialogFragment.this.getDialog().dismiss();
                        getActivity().recreate();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        InputDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
