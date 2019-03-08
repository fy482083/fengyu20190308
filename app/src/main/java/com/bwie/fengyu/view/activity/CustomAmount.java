package com.bwie.fengyu.view.activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bwie.fengyu.R;

public class CustomAmount extends LinearLayout {
    Context context;
    private int num = 1;
    private OnAmountLisenter onAmountLisenter;
    private Button btn_jia,btn_jian;
    private EditText edit_text;

    public interface OnAmountLisenter {
        void onAmountLisenter(int num);
    }

    public CustomAmount(Context context) {
        super(context);
    }

    public void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_num, null, false);
        addView(view);
        btn_jia = view.findViewById(R.id.btn_jia);
        btn_jian = view.findViewById(R.id.btn_jian);
        edit_text = view.findViewById(R.id.edit_text);
    }

}
