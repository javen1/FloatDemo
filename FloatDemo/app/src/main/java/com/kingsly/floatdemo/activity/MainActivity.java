package com.kingsly.floatdemo.activity;

import com.kingsly.floatdemo.R;
import com.kingsly.floatdemo.base.BaseActivity;
import com.kingsly.floatdemo.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.btJump.setOnClickListener(v -> {
            SecondActivity.launchActivity(this);
        });
    }
}