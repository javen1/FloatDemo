package com.kingsly.floatdemo.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kingsly.floatdemo.R;
import com.kingsly.floatdemo.activity.SecondActivity;
import com.kingsly.floatdemo.mode.ModeBean;
import com.kingsly.floatdemo.utils.AppUtils;
import com.kingsly.floatdemo.utils.CloseActivityUtils;
import com.kingsly.floatdemo.utils.ScreenUtils;

/**
 * @author : kingsly
 * @date : On 2021/3/1
 */
public class QueueUpFloatService extends Service {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View floatView;
    private ScaleAnimation buttonScale;
    public static String KEY_MODEL = "key_model";


    /**
     * 启动服务并传值
     *
     * @param activity 启动服务的activity
     * @param modeBean 数据对象
     */
    public static void launchService(Activity activity, ModeBean modeBean) {
        try {
            Intent intent = new Intent(activity, QueueUpFloatService.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_MODEL, modeBean);
            intent.putExtras(bundle);
            activity.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onCreate() {
        super.onCreate();
        //加一点简单的动画 让效果更漂亮点
        buttonScale = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_float);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = ScreenUtils.dp2px(66);
        layoutParams.height = ScreenUtils.dp2px(66);
        layoutParams.x = ScreenUtils.getRealWidth() - ScreenUtils.dp2px(60);
        layoutParams.y = ScreenUtils.deviceHeight() * 2 / 3;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("InflateParams")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ModeBean modeBean = (ModeBean) intent.getExtras().getSerializable(KEY_MODEL);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        floatView = layoutInflater.inflate(R.layout.view_float, null);
        RelativeLayout rlFloatParent = floatView.findViewById(R.id.rl_float_parent);
        rlFloatParent.startAnimation(buttonScale);
        TextView tvIndex = floatView.findViewById(R.id.tv_queue_index);
        tvIndex.setText(modeBean.title);
        floatView.findViewById(R.id.iv_close_float).setOnClickListener(v -> stopSelf());
        floatView.setOnTouchListener(new FloatingOnTouchListener());
        windowManager.addView(floatView, layoutParams);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null == floatView) {
            return;
        }
        windowManager.removeView(floatView);
    }


    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private long downTime;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downTime = System.currentTimeMillis();
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                case MotionEvent.ACTION_UP:
                    /* *
                     * 这里根据手指按下和抬起的时间差来判断点击事件还是滑动事件
                     * */
                    if ((System.currentTimeMillis() - downTime) < 200) {
                        if (AppUtils.isAppIsInBackground()) {
                            AppUtils.moveToFront(CloseActivityUtils.activityList.get(CloseActivityUtils.activityList.size() - 1).getClass());
                        } else {
                            if (!CloseActivityUtils.activityList.get(CloseActivityUtils.activityList.size() - 1)
                                    .getClass().getSimpleName().contains("SecondActivity")) {
                                SecondActivity.launchActivity(CloseActivityUtils.activityList.get(CloseActivityUtils.activityList.size() - 1));
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}