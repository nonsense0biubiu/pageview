package com.nonsense.pageviews.View;

import android.content.Context;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.nonsense.pageviews.R;

import java.util.List;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class MyPickerView {
    private Context mContext;

    private OptionsPickerView pickerView;
    private PickViewListenner listenner;

    private int Row=1; //默认列数1


    public MyPickerView(Context context, PickViewListenner mlistener) {
        mContext = context;
        listenner = mlistener;
        init(mContext);

    }

    public MyPickerView(Context context, PickViewListenner mlistener, List list) {
        mContext = context;
        listenner = mlistener;
        init(mContext);
        setDatas(list);

    }
    /**
     * 设置一列的
     */
    public void setDatas(List list) {
        if (pickerView != null) {
            pickerView.setPicker(list);
            Row=1;
        }
    }
    /**
     * 设置二列的
     */
    public void setDatas(List list, List list2) {
        if (pickerView != null) {
            pickerView.setPicker(list, list2);
            Row=2;
        }
    }

    /**
     * 设置三列的
     * @param list1
     * @param list2
     * @param list3
     */
    public void setDatas(List list1, List list2, List list3) {
        if (pickerView != null) {
            pickerView.setPicker(list1, list2, list3);
            Row=3;
        }
    }

    private void init(final Context context) {
        pickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                listenner.clickItem(options1,options2,options3);
            }
        }).setLayoutRes(R.layout.pickview_fz_choose, new CustomListener() {
            @Override
            public void customLayout(View v) {
                v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickerView.dismiss();
                    }
                });
                v.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickerView.returnData();
                        pickerView.dismiss();
                    }
                });
            }

        }).isDialog(false).build();
    }

    public void showPickView() {
        pickerView.show();
    }

    public interface PickViewListenner {
        void clickItem(int pos1, int pos2, int pos3);
    }

    /**
     * 设置当前选择项
     * @param pos1
     */
    public void setCurrentOptions(int pos1){
        if (pickerView!=null)
        {
            pickerView.setSelectOptions(pos1);
        }
    }

    /**
     * 设置当前选择项
     * @param pos1
     * @param pos2
     */
    public void setCurrentOptions(int pos1,int pos2){
        if (pickerView!=null)
        {
            pickerView.setSelectOptions(pos1,pos2);
        }
    }

    /**
     * 设置当前选择项
     * @param pos1
     * @param pos2
     * @param pos3
     */
    public void setCurrentOptions(int pos1,int pos2,int pos3){
        if (pickerView!=null)
        {
            pickerView.setSelectOptions(pos1,pos2,pos3);
        }
    }
}
