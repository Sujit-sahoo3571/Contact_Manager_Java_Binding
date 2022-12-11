package com.iamwithinyou.contactmanagerappdatabinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact extends BaseObservable {
    private String name;
    private String email;

    @PrimaryKey(autoGenerate = true)
    private int id;


    @Ignore
    public Contact(){

    }

//    public Contact(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }

    public Contact(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
