package com.nonsense.pageviews.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nonsense.pageviews.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nonsense on 2017/10/31.
 */

public class PageView extends LinearLayout {


    private Context context; //上下文

    private PageViewClick pageViewClick; //翻页监听

    private int index = 0; //当前页

    private int totalpage = 0; //最大页

    private MyPickerView pickerView; //页面选择器

    private boolean isNet = false; //是否通过网络刷新页数  ,如果true,就需要在网络回调重新设置当前页和最大页，调用setPageNo(int currentpage, int totalpage)方法.false的话，不需要手动调用，但是无法获取接口最新的页数

    private List<PageBean> pickpagelist; //储存页码的list

    public void setNet(boolean net) {
        isNet = net;
    }

    public int getIndex() {
        return index;
    }

    /**
     * 手动设置当前页
     *
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
        RefreshCurrentPageView();
    }

    public int getTotalpage() {
        return totalpage;
    }

    /**
     * 手动设置最大页
     *
     * @param totalpage
     */
    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
        setPickViewData(totalpage);
        RefreshCurrentPageView();
    }

    /**
     * 手动设置当前页，最大页
     *
     * @param currentpage
     * @param totalpage
     */
    public void setPageNo(int currentpage, int totalpage) {
        this.index = currentpage;
        this.totalpage = totalpage;
        setPickViewData(totalpage);
        RefreshCurrentPageView();
    }

    /**
     * 手动设置当前页，最大页(页面从1开始计数)
     *
     * @param currentpage
     * @param totalpage
     */
    protected void setPageNoFromOne(int currentpage, int totalpage) {
        this.index = currentpage - 1;
        this.totalpage = totalpage;
        RefreshCurrentPageView();
    }

    private TextView prepage, nextpage, currentpage;

    public PageView(Context context) {
        super(context);
        initview(context);

    }

    public PageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initview(Context context) {
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pageview_layout, null);

        initPickView();

        prepage = (TextView) view.findViewById(R.id.pre_page);

        nextpage = (TextView) view.findViewById(R.id.next_page);

        currentpage = (TextView) view.findViewById(R.id.tv_num_page);

        prepage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageViewClick != null && index > 0) {
                    pageViewClick.ClickPrePage(index);
                    if (!isNet) { //非网络情况下手动回调
                        index--;
                        RefreshCurrentPageView();
                    }
                }
            }
        });

        nextpage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageViewClick != null && index + 1 < totalpage) {
                    pageViewClick.ClickNextPage(index);
                    if (!isNet) { //非网络情况下手动回调
                        index++;
                        RefreshCurrentPageView();
                    }
                }

            }
        });

        currentpage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickerView != null && pickpagelist != null && !pickpagelist.isEmpty()) {
                    pickerView.setCurrentOptions(index);
                    pickerView.showPickView();
                }
            }
        });
        this.setOrientation(VERTICAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        addView(view);
    }

    private void setPickViewData(int max) {
        pickpagelist.clear();
        for (int i = 0; i < max; i++) {
            pickpagelist.add(new PageBean(i));
        }
    }

    /**
     * 初始化pickview
     */
    private void initPickView() {
        pickpagelist = new ArrayList<>();

        pickerView = new MyPickerView(context, new MyPickerView.PickViewListenner() {
            @Override
            public void clickItem(int pos1, int pos2, int pos3) {

                if (pageViewClick != null && pickpagelist != null && !pickpagelist.isEmpty()) {
                    if ((pickpagelist.get(pos1).getPage()) != index) {
                        pageViewClick.ClickPageNo(pickpagelist.get(pos1).getPage());
                        index=pickpagelist.get(pos1).getPage();
                        RefreshCurrentPageView();
                    }
                } else {
                    Log.e(this.getClass().getName(), "没有更多分页可以选择");
                }
            }
        });
        pickerView.setDatas(pickpagelist);

    }

    /**
     * 设置按钮亮或者灰
     *
     * @param textView
     * @param isGray
     */
    private void setButtonGray(TextView textView, boolean isGray) {


        if (textView == null) {
            return;
        }
        if (isGray) {
            textView.setTextColor(context.getResources().getColor(R.color.gray_enable));
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.blue));
        }
    }

    /**
     * 刷新整个view
     */
    public void RefreshCurrentPageView() {
        setButtonGray(nextpage, false);
        setButtonGray(prepage, false);
        if (totalpage <= index) { //判断是否index 大于总数
            index = totalpage-1;
        }

        if (index >= totalpage - 1) { //判断下一页是否可用
            setButtonGray(nextpage, true);
        }

        if (index == 0) { //判断是否是第一页
            setButtonGray(prepage, true);
        }
        if (currentpage == null) { //判断是否实例化了
            return;
        }
        if (pickpagelist != null && !pickpagelist.isEmpty()) { //判断分页存储list是否存在
            pickerView.setCurrentOptions(index);
        }
        if (totalpage==0) //判断是否没有最大页数
        {
            index=0;
            currentpage.setText((index) + "/" + (totalpage));
        }
        else
        {
            currentpage.setText((index + 1) + "/" + (totalpage));
        }

    }


    private PageViewClick getPageViewClick() {
        return pageViewClick;
    }

    /**
     * 设置监听，必调用
     *
     * @param pageViewClick
     */
    public void setPageViewClick(PageViewClick pageViewClick) {
        this.pageViewClick = pageViewClick;
    }

    public interface PageViewClick {
        void ClickPrePage(int index);

        void ClickNextPage(int index);

        void ClickPageNo(int page);
    }


}
