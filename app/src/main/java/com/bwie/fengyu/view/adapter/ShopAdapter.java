package com.bwie.fengyu.view.adapter;

import android.content.Context;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.fengyu.R;
import com.bwie.fengyu.model.bean.ShopCarBean;

public class ShopAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ShopCarBean mShopBean;

    public ShopAdapter(Context mContext, ShopCarBean mShopBean) {
        this.mContext = mContext;
        this.mShopBean = mShopBean;
    }

    @Override
    public int getGroupCount() {
    if (mShopBean==null){
        return 0;
    }
       return mShopBean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mShopBean==null){
            return 0;
        }
        return mShopBean.getData().get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
           ParentViewHolder parentViewHolder;
           if (convertView==null){
               convertView= LayoutInflater.from(mContext).inflate(R.layout.group_item,null);
               parentViewHolder=  new ParentViewHolder();
               parentViewHolder.check_group=convertView.findViewById(R.id.check_group);
               parentViewHolder.text_group=convertView.findViewById(R.id.text_group);
               convertView.setTag(parentViewHolder);
           }else{
               parentViewHolder = (ParentViewHolder) convertView.getTag();
           }
           parentViewHolder.text_group.setText(mShopBean.getData().get(groupPosition).getSellerName());
           parentViewHolder.check_group.setChecked(mShopBean.getData().get(groupPosition).isCheck());
           parentViewHolder.check_group.setTag(groupPosition);
           parentViewHolder.check_group.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                   boolean checked = checkBox.isChecked();
                   int groupPosition = Integer.parseInt(checkBox.getTag().toString());
                   mShopBean.getData().get(groupPosition).setCheck(checked);
                   selectGroup(groupPosition,checked);
                   boolean selectAllGroup = isSelectAllGroup();
                   mCheckBox.setChecked(selectAllGroup);
                   notifyDataSetChanged();
               }
           });
             return convertView;
    }

    private void selectGroup(int groupPosition, boolean checked) {
        for (int i = 0; i <mShopBean.getData().get(groupPosition).getList().size() ; i++) {
            ShopCarBean.DataBean.ListBean listBean = mShopBean.getData().get(groupPosition).getList().get(i);
            listBean.setCheck(checked);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       ChildViewHolder childViewHolder;
       if (convertView==null){
           convertView= LayoutInflater.from(mContext).inflate(R.layout.child_item,null);
           childViewHolder = new ChildViewHolder();
           childViewHolder.check_child=convertView.findViewById(R.id.check_child);
           childViewHolder.text_child=convertView.findViewById(R.id.text_child);
           childViewHolder.price_item=convertView.findViewById(R.id.price_item);
           childViewHolder.img_view=convertView.findViewById(R.id.img_view);
           convertView.setTag(childViewHolder);
       }else{
           childViewHolder= (ChildViewHolder) convertView.getTag();
       }
       childViewHolder.price_item.setText(mShopBean.getData().get(groupPosition).getList().get(childPosition).getPrice());
        //Glide.with(mContext).load("url").into(mShopBean.getData().get(groupPosition).getList().get(childPosition).getImages());
       childViewHolder.text_child.setText(mShopBean.getData().get(groupPosition).getList().get(childPosition).getTitle());
       childViewHolder.check_child.setChecked(mShopBean.getData().get(groupPosition).getList().get(childPosition).isCheck());
       childViewHolder.check_child.setTag(groupPosition+"#"+childPosition);
       childViewHolder.check_child.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CheckBox checkBox = (CheckBox) v;
               String  tag = (String) v.getTag();
               int groupPosition = Integer.parseInt(tag.split("#")[0]);
               int childPosition = Integer.parseInt(tag.split("#")[1]);
               ShopCarBean.DataBean dataBean = mShopBean.getData().get(groupPosition);
               ShopCarBean.DataBean.ListBean goodsbean = dataBean.getList().get(childPosition);
               goodsbean.setCheck(checkBox.isChecked());
               boolean selectGroup = isSelectGroup(groupPosition);
                dataBean.setCheck(selectGroup);
               boolean selectAllGroup = isSelectAllGroup();
               mCheckBox.setChecked(selectAllGroup);
               notifyDataSetChanged();

           }
       });
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public boolean isSelectGroup(int groupPosition) {
        for (int i = 0; i < mShopBean.getData().get(groupPosition).getList().size(); i++) {
            ShopCarBean.DataBean.ListBean listBean = mShopBean.getData().get(groupPosition).getList().get(i);
            boolean check = listBean.isCheck();
            if (!check){
                return false;
            }
        }return true;

    }

    private boolean isSelectAllGroup() {
        for (int i = 0; i < mShopBean.getData().size(); i++) {
            ShopCarBean.DataBean dataBean = mShopBean.getData().get(i);
            boolean check = dataBean.isCheck();
            if (!check){
                return false;
            }

        }

        return true;
    }

     CheckBox mCheckBox;
    public void setCheckBox(CheckBox checkBox) {
         this.mCheckBox=checkBox;
          mCheckBox.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  CheckBox checkBox  = (CheckBox) v;
                  SelectAll(checkBox.isChecked());
              }
          });

    }

    private void SelectAll(boolean checked) {
        for (int i = 0; i <mShopBean.getData().size(); i++) {
            ShopCarBean.DataBean dataBean = mShopBean.getData().get(i);
            dataBean.setCheck(checked);
            for (int j = 0; j <dataBean.getList().size() ; j++) {
                ShopCarBean.DataBean.ListBean listBean = dataBean.getList().get(j);
                listBean.setCheck(checked);
            }
        }
    }
}
        class ParentViewHolder{
            CheckBox check_group;
            TextView text_group;
        }

        class ChildViewHolder{
            CheckBox check_child;
            TextView text_child,price_item;
            ImageView img_view;
        }