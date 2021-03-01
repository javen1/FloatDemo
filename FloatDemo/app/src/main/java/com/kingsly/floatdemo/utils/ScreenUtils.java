package com.kingsly.floatdemo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.kingsly.floatdemo.base.BaseApplication;

/**
 * @author : kingsly
 * @date : On 2021/3/1
 */
public class ScreenUtils {

    public static final float DENSITY = Resources.getSystem()
            .getDisplayMetrics().density;
    @SuppressLint("StaticFieldLeak")
    private static final Context mContext = BaseApplication.mContext;

    /**
     * dp 转 px
     *
     * @param dpValue 以 dp 为单位的值
     * @return px value
     */
    public static int dp2px(int dpValue) {
        return (int) (dpValue * DENSITY + 0.5f);
    }

    /**
     * 获取屏幕真实宽度（去除虚拟按键和状态栏）
     *
     * @return px
     */
    public static int getRealWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenWidth = 0;
        DisplayMetrics dm = new DisplayMetrics();
        display.getRealMetrics(dm);
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        return screenWidth;
    }

    /**
     * 获取设备高度（px）
     *
     * @return
     */
    public static int deviceHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }
}