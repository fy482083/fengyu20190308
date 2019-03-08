package com.bwie.fengyu.presenter;
public class BasePresenter <V>{
    private V mview;
    public void setView(V v) {
        this.mview=v;
    }

    public V getView() {
        return mview;
    }
    public void DettachView(){
        this.mview=null;
    }
}
