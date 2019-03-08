package com.bwie.fengyu.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bwie.fengyu.R;
import com.bwie.fengyu.model.bean.ShopCarBean;
import com.bwie.fengyu.presenter.MainPresenter;
import com.bwie.fengyu.view.adapter.ShopAdapter;
import com.bwie.fengyu.view.interfaces.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView {

    private MainPresenter mainPresenter;
    private ExpandableListView mExpandableListView;
    private CheckBox mCheckAll;
    /**
     * ￥00
     */
    private TextView mTextPricrall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {

        mExpandableListView = findViewById(R.id.expandable_listView);
        mCheckAll = findViewById(R.id.check_all);
        mTextPricrall = (TextView) findViewById(R.id.text_pricrall);
    }

    private void initData() {
        mainPresenter = new MainPresenter();
        mainPresenter.setView(this);
       mainPresenter.getShopData();

    }

    @Override
    public void onSuccess(Object data) {
        ShopCarBean shopCarBean = (ShopCarBean) data;
        Log.i("aaa", "onSuccess: " + data);
        ShopAdapter shopAdapter = new ShopAdapter(this,shopCarBean);
        mExpandableListView.setAdapter(shopAdapter);
        for (int i = 0; i <shopCarBean.getData().size() ; i++) {
            mExpandableListView.expandGroup(i);
        }
       shopAdapter.setCheckBox(mCheckAll);
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.DettachView();
    }
}
