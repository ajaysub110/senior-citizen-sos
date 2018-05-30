package com.example.android.solochana;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

/**
 * Created by ajays on 22-05-2018.
 */

public class RAdapter extends RecyclerView.Adapter<RAdapter.ContactsViewHolder> implements RealmChangeListener<RealmResults<Contact>>{

    private ArrayList<Contact> contacts;
    private Realm db;
    private Context appContext;
    private Intent parentIntent;
    public int callsMade;
    ListActivity listActivity;

    public RAdapter(ListActivity listActivity, Intent intent) {
        this.parentIntent = intent;
        this.listActivity = listActivity;
        this.contacts = new ArrayList<>();
        db = Realm.getDefaultInstance();
        loadData(intent);
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {

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
                            callsMade += 1;
                            Log.i("callsMade", "Calls made: " + callsMade);
                            Log.i("itemCount", "Item count: " + getItemCount());
                            view.getContext().startActivity(intent);//call activity and make phone call
                            Toast.makeText(getApplicationContext(), "Calling " + contact.getName(), Toast.LENGTH_SHORT).show();
                            if(callsMade >= getItemCount()) {
                                if(parentIntent.getIntExtra("level", 1)==1){
                                    listActivity.level2Button.setVisibility(View.VISIBLE);
                                }
                                else if(parentIntent.getIntExtra("level", 1)==2){
                                    listActivity.homeButton.setVisibility(View.VISIBLE);
                                }
                            }
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

    public void loadData(Intent intent) {
        RealmQuery<Contact> query = db.where(Contact.class).equalTo("level", intent.getIntExtra("level",1));

        final RealmResults<Contact> results = query.findAll();

        for (Contact c :
                results) {
            contacts.add(db.copyFromRealm(c));
        }
        results.addChangeListener(this);
        notifyDataSetChanged();
        db.close();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ContactsViewHolder(contactView);
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, final int position) {
        holder.populateViews(this.contacts.get(position));
        holder.addListeners(this.contacts.get(position));
        if(parentIntent.getAction().equals("ViewContacts")){
            holder.callButton.setVisibility(View.GONE);
        }
        else {
        }
    }

    @Override
    public void onChange(@NonNull RealmResults<Contact> results) {
        for (Contact c : results) {
            contacts.add(db.copyFromRealm(c));
        }
        results.addChangeListener(this);
        notifyDataSetChanged();
    }
}
