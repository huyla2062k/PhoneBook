package com.laduchuy.phonebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laduchuy.phonebook.databinding.MainfragmentLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    MainfragmentLayoutBinding binding;
    List<Contact> contactList;
    AdapterContact adapterContact;
    SQLHelper sqlHelper;
   public static Contact contact;


    public static MainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.mainfragment_layout, container, false);
        contactList = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());
        contactList = sqlHelper.getAllPhoneNumber();
        final Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        adapterContact.setiOnClickContact(new IOnClickContact() {
            @Override
            public void onClickName(String name, int position) {
                phoneIntent.setData(Uri.parse("tel:" + contactList.get(position).getPhoneNumber()));

            }

            @Override
            public void onClickPhoneNumber(String phone, int position) {
                phoneIntent.setData(Uri.parse("tel:" + contactList.get(position).getPhoneNumber()));
            }

            @Override
            public void onClickMenu(View v, final int position, int img) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu_choise, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mnFix:
                                contact = contactList.get(position);
                                FixFragment.newInstance();
                                break;
                            case R.id.mnDelete:
                                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                        .setMessage("Are you sure you want to delete this phone number?")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(sqlHelper.deletePhoneNumber(String.valueOf(position))!=0) {
                                                    Toast.makeText(getContext(), "Delete successfull", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(getContext(), "Delete fail", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getContext(),"Cancel",Toast.LENGTH_SHORT).show();
                                            }
                                        }).create();
                                alertDialog.show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }

        });



        adapterContact = new AdapterContact(contactList);
        binding.rvPhone.setAdapter(adapterContact);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvPhone.setLayoutManager(layoutManager);


        return binding.getRoot();
    }
}
