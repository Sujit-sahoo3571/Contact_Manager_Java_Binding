package com.iamwithinyou.contactmanagerappdatabinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.iamwithinyou.contactmanagerappdatabinding.databinding.ActivityAddNewContactBinding;

public class AddNewContactActivity extends AppCompatActivity {
    private ActivityAddNewContactBinding activityAddNewContactBinding;
    private Contact contact;
    private AddNewContactActivityClickHandlers handlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        contact = new Contact();
        activityAddNewContactBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_contact);
        activityAddNewContactBinding.setContact(contact);

        handlers = new AddNewContactActivityClickHandlers(this);
        activityAddNewContactBinding.setClickHandler(handlers);
    }

    public class AddNewContactActivityClickHandlers{
        Context context;

        public AddNewContactActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onSubmit(View view){
            if (contact.getName() == null ){
                Toast.makeText(context, "Name Field can't be empty!", Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent();
                i.putExtra("NAME", contact.getName());
                i.putExtra("EMAIL", contact.getEmail());
                setResult(RESULT_OK, i);
                finish();

            }

        }
    }
}