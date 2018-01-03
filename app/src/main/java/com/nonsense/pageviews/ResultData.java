package com.nonsense.pageviews;

import java.util.List;

/**
 * Created by nonsense on 2018/1/3.
 */

public class ResultData {
    private int CountPage;

    private int CurrentIndex;

    private List<String> datas;

    public int getCountPage() {
        return CountPage;
    }

    public void setCountPage(int countPage) {
        CountPage = countPage;
    }

    public int getCurrentIndex() {
        return CurrentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        CurrentIndex = currentIndex;
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
