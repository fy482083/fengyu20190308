package com.bwie.fengyu.presenter;

import com.bwie.fengyu.model.bean.ShopCarBean;
import com.bwie.fengyu.model.httputils.HttpUtils;
import com.bwie.fengyu.view.interfaces.IMainView;

import java.io.IOException;


public class MainPresenter extends BasePresenter<IMainView>  {


    private final HttpUtils httpUtils;

    public MainPresenter(){
        httpUtils = HttpUtils.getInstance();
    }

    public void getShopData() {
        httpUtils.getData("http://172.17.8.100/ks/product/getCarts?uid=51", ShopCarBean.class, new HttpUtils.CallBackData() {
            @Override
            public void onResponse(Object data) {
                getView().onSuccess(data);
            }

            @Override
            public void onFailure(IOException e) {

            }
        });
    }
}
