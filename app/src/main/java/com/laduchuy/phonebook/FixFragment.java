package com.laduchuy.phonebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.laduchuy.phonebook.databinding.FixfragmentLayoutBinding;

import java.util.regex.Pattern;

public class FixFragment extends Fragment {
    FixfragmentLayoutBinding binding;

    SQLHelper sqlHelper;

    public static FixFragment newInstance() {

        Bundle args = new Bundle();

        FixFragment fragment = new FixFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fixfragment_layout,container,false);
        sqlHelper=new SQLHelper(getContext());
        final String gex="^(07[09768]|08[12345]|03[2-9]|05[689]|09[0-9])[0-9]{7}$";
        binding.etName.setText(MainFragment.contact.getName());
        binding.etPhoneNumber.setText(MainFragment.contact.getPhoneNumber());
        final String name = binding.etName.getText().toString();
        final String phoneNumber = binding.etPhoneNumber.getText().toString();


        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Pattern.matches(gex,phoneNumber)){
                    sqlHelper.insertPhoneNumber(new Contact(name,phoneNumber));
                    MainFragment.newInstance();
                }
                else {
                    Toast.makeText(getContext(),"incorrect phone number",Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment.newInstance();
                Toast.makeText(getContext(),"Cancel",Toast.LENGTH_SHORT).show();
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
