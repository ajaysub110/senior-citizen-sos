package com.example.android.solochana;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ajays on 22-05-2018.
 */

public class RAdapter extends RecyclerView.Adapter<ContactsViewHolder> implements RealmChangeListener<RealmResults<Contact>>{

    private ArrayList<Contact> contacts;
    private Realm db;

    public RAdapter(int lvl) {
        this.contacts = new ArrayList<>();
        db = Realm.getDefaultInstance();
        loadData(lvl);
    }

    public void loadData(int lvl) {
        RealmQuery<Contact> query = db.where(Contact.class).equalTo("level", lvl);

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
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        holder.populateViews(this.contacts.get(position));
    }

    @Override
    public void onChange(@NonNull RealmResults<Contact> results) {
        for (Contact c :
                results) {
            contacts.add(db.copyFromRealm(c));
        }
        results.addChangeListener(this);
        notifyDataSetChanged();
    }
}
