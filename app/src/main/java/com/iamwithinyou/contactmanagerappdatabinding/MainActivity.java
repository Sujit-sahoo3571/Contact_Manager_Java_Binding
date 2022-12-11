package com.iamwithinyou.contactmanagerappdatabinding;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.iamwithinyou.contactmanagerappdatabinding.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ContactAppDatabase contactAppDatabase;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ContactDataAdapter contactDataAdapter ;

    //Binding
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler clickHandler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Data Binding
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        clickHandler  = new MainActivityClickHandler(this);
        activityMainBinding.setClickHandlers(clickHandler);

        //Recycler View
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView recyclerView = activityMainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Adapter
        contactDataAdapter = new ContactDataAdapter(contacts);
        recyclerView.setAdapter(contactDataAdapter);

        //Database
         contactAppDatabase = Room.databaseBuilder(getApplicationContext(),
                 ContactAppDatabase.class, "ContactDB").build();
         
         // AppDATa
        LoadData();

        //Handling Swapping
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contact = contacts.get(viewHolder.getAdapterPosition());
                deleteContact(contact);
            }
        }).attachToRecyclerView(recyclerView);

        // callback result or add contact


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK ){
            String Name = data.getStringExtra("NAME");
            String Email = data.getStringExtra("EMAIL");
            Contact contact = new Contact(Name, Email,0);
            addNewContact(contact);

//            contactDataAdapter.notifyDataSetChanged();

        }
    }

    private void addNewContact(Contact contact) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background Task
                contactAppDatabase.contactDAO().insert(contact);
                contacts.add(contact);

                // on post task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        LoadData();
                        contactDataAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    private void deleteContact(Contact contact) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background Task
            contactAppDatabase.contactDAO().delete(contact);
            contacts.remove(contact);

                // on post task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        LoadData();
                        contactDataAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    private void LoadData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background Task
//                contacts = (ArrayList<Contact>) contactAppDatabase.contactDAO().getAllContacts();
                contacts.addAll( contactAppDatabase.contactDAO().getAllContacts() );


                // on post task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contactDataAdapter.setContacts(contacts);
                        contactDataAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    public class MainActivityClickHandler{
        Context context ;

        public MainActivityClickHandler(Context context) {
            this.context = context;
        }

        public void onFABClick (View view ){
            Intent i = new Intent(MainActivity.this, AddNewContactActivity.class);
          startActivityForResult(i, 1);
        }
    }
}