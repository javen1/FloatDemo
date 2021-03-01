package com.kingsly.floatdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kingsly.floatdemo.R;
import com.kingsly.floatdemo.base.BaseActivity;
import com.kingsly.floatdemo.databinding.ActivitySecondBinding;
import com.kingsly.floatdemo.mode.ModeBean;
import com.kingsly.floatdemo.service.QueueUpFloatService;
import com.kingsly.floatdemo.utils.CloseActivityUtils;

/**
 * @author : kingsly
 * @date : On 2021/3/1
 */
public class SecondActivity extends BaseActivity<ActivitySecondBinding> {

    private ModeBean modeBean;
    public static int REQUEST_CODE = 0x00;

    public static void launchActivity(Activity act) {
        try {
            Intent intent = new Intent(act, SecondActivity.class);
            act.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void initData() {
        modeBean = new ModeBean("3");
    }

    @Override
    protected void setListener() {
        mBinding.btOpenFloat.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), REQUEST_CODE);
            } else {
                QueueUpFloatService.launchService(this, modeBean);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            QueueUpFloatService.launchService(this, modeBean);
        }
    }
}