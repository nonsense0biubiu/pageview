package com.nonsense.pageviews;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nonsense.pageviews.View.PageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    private RecyclerView recyclerView;
    private PageView pageView;
    private MyAdapter adapter;
    private List<String> datas;

    private int page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_demo);

        initView();
    }


    private void initView() {
        datas = new ArrayList<>();
        recyclerView = findViewById(R.id.review);
        pageView = findViewById(R.id.pageview);
        adapter = new MyAdapter(this, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        initPageView();
        ResultData resultData = testGetData(page);
        afterGetData(resultData);
    }

    private void initPageView() {

        pageView.setNet(true); //设置是不是网络请求数据
        pageView.setPageViewClick(new PageView.PageViewClick() {
            @Override
            public void ClickPrePage(int index) {
                //上一页
                page--;
                ResultData resultData = testGetData(page);
                afterGetData(resultData);
            }

            @Override
            public void ClickNextPage(int index) {
                //下一页
                page++;
                ResultData resultData = testGetData(page);
                afterGetData(resultData);
            }

            @Override
            public void ClickPageNo(int page1) {
                //直接点击页码可选择页数
                page = page1;
                ResultData resultData = testGetData(page);
                afterGetData(resultData);
            }
        });

    }

    /**
     * 处理拿到数据后的回调
     *
     * @param data
     */
    private void afterGetData(ResultData data) {

        pageView.setPageNo(data.getCurrentIndex(), data.getCountPage()); //设置页码 特别注意 currentindex 是从0开始  countPage是从1开始计算的
        datas.clear();
        datas.addAll(data.getDatas());
        adapter.notifyDataSetChanged();

    }


    //模拟服务端发送数据
    public ResultData testGetData(int index) {

        ResultData resultData = new ResultData();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("第"+index+"页"+i + "个");
        }
        resultData.setDatas(list);
        resultData.setCountPage(7);
        resultData.setCurrentIndex(index);
        return resultData;
    }
}
