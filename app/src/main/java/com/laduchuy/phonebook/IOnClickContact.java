package com.laduchuy.phonebook;

import android.view.View;

public interface IOnClickContact {
    void onClickName(String name,int position);
    void onClickPhoneNumber(String phone,int position);
    void onClickMenu(View v,int position,int img);

}
