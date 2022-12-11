package com.iamwithinyou.contactmanagerappdatabinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iamwithinyou.contactmanagerappdatabinding.databinding.ContactListItemBinding;

import java.util.ArrayList;

public class ContactDataAdapter extends RecyclerView.Adapter<ContactDataAdapter.ContactViewHolder> {

    ArrayList<Contact> contacts ;

    public ContactDataAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    //inner class viewholder
    public class ContactViewHolder extends  RecyclerView.ViewHolder{
//        TextView name, email ;
        private ContactListItemBinding contactListItemBinding;

        public ContactViewHolder(@NonNull ContactListItemBinding contactListItemBinding) {
            super(contactListItemBinding.getRoot());
            this.contactListItemBinding = contactListItemBinding;
//            this.name = itemView.findViewById(R.id.tv_name);
//            this.email = itemView.findViewById(R.id.tv_email);
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).
//                inflate(R.layout.contact_list_item, parent,false);
//
//        return new ContactViewHolder(view);

        ContactListItemBinding contactListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.contact_list_item,
                parent,
                false
        );
        return  new ContactViewHolder(contactListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
//        holder.name.setText(contact.getName());
//        holder.email.setText(contact.getEmail());
        holder.contactListItemBinding.setContact(contact);

    }

    @Override
    public int getItemCount() {
        if (contacts != null)
        return contacts.size();
        else return 0;
    }

    //set datachange for contact list
    public void setContacts(ArrayList<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

}
