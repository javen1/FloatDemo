package com.kingsly.floatdemo.activity;

import android.content.Intent;

import com.kingsly.floatdemo.R;
import com.kingsly.floatdemo.base.BaseActivity;
import com.kingsly.floatdemo.databinding.ActivityMainBinding;
import com.kingsly.floatdemo.service.QueueUpFloatService;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, QueueUpFloatService.class));
    }
}