package com.nenggou.slsm.splash.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class GuideAdapter extends PagerAdapter {

    //引导页面列表
    private List<View> views;
    private Activity activity;


    public GuideAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return  view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        return views.get(position);
    }
}
