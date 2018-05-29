package com.example.android.solochana;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

/**
 * Created by ajays on 22-05-2018.
 */

public class ContactsViewHolder extends RecyclerView.ViewHolder{

    private TextView nameTextview;
    private TextView phoneTextview;
    public Button callButton;

    public ContactsViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View self) {
        nameTextview = (TextView) self.findViewById(R.id.name_textview);
        phoneTextview = (TextView) self.findViewById(R.id.phone_textview);
        callButton = (Button) self.findViewById(R.id.call_button);
    }

    public void addListeners(final Contact contact){
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+contact.getPhone()));
                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Please turn on Phone permission", Toast.LENGTH_SHORT).show();
                }else {     //have got permission
                    try{
                        view.getContext().startActivity(intent);//call activity and make phone call
                        Toast.makeText(getApplicationContext(), "Calling " + contact.getName(), Toast.LENGTH_SHORT).show();
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"Your activity was not found",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void populateViews(final Contact contact) {
        nameTextview.setText(contact.getName());
        phoneTextview.setText(contact.getPhone());
    }
}
