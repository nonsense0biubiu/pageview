package com.nonsense.pageviews.View;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by nonsense on 2017/11/22.
 */

public class PageBean implements IPickerViewData{

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String getPickerViewText() {
        return (this.page+1)+"";
    }

    public PageBean(int page) {
        this.page = page;
    }

    public PageBean() {
    }
}
