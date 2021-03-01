package com.kingsly.floatdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.kingsly.floatdemo.utils.CloseActivityUtils;

/**
 * @author : kingsly
 * @date : On 2021/3/1
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    public Context mContext;
    protected T mBinding;

    /***
     * 填充布局
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        CloseActivityUtils.activityList.add(this);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initData();
        setViewData();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void initData() {

    }

    protected void setViewData() {

    }

    protected void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CloseActivityUtils.removeActivity(this);
    }
}