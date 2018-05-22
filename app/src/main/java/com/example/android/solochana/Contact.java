package com.example.android.solochana;

import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ajays on 22-05-2018.
 */

public class Contact extends RealmObject{

    public Contact() {
        name = "";
        phone = "";
        level = 0;
    }

    @NonNull
    public String name;

    @PrimaryKey
    public String phone;

    @NonNull
    public int level;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    public int getLevel() {
        return level;
    }

    public void setLevel(@NonNull int level) {
        this.level = level;
    }
}
