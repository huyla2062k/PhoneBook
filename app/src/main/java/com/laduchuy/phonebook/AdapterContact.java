package com.laduchuy.phonebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolder> {
    List<Contact> contactList;
    IOnClickContact iOnClickContact;

    public void setiOnClickContact(IOnClickContact iOnClickContact) {
        this.iOnClickContact = iOnClickContact;
    }

    public AdapterContact(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public AdapterContact.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContact.ViewHolder holder, final int position) {
        final Contact contact = contactList.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhonNumber.setText(contact.getPhoneNumber());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickContact.onClickName(contact.getName(),position);
            }
        });

        holder.tvPhonNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickContact.onClickPhoneNumber(contact.getPhoneNumber(),position);
            }
        });
        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickContact.onClickMenu(v,R.drawable.ic_baseline_menu_24,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhonNumber;
        ImageView imgMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhonNumber = itemView.findViewById(R.id.tvPhoneNumber);
            imgMenu = itemView.findViewById(R.id.imgMenu);

        }
    }
}
