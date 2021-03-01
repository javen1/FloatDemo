package com.kingsly.floatdemo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.kingsly.floatdemo.base.BaseApplication;

import java.util.List;

/**
 * @author : kingsly
 * @date : On 2021/3/1
 */
public class AppUtils {


    /**
     * 将app从后台唤醒到前台
     */
    public static void moveToFront(final Class Class) {
        ActivityManager activityManager = (ActivityManager) BaseApplication.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(20);
        if (taskInfoList == null) {
            return;
        }
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
            if (taskInfo.baseActivity.getPackageName().equals(BaseApplication.mContext.getPackageName())) {
                activityManager.moveTaskToFront(taskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
                Intent intent = new Intent(BaseApplication.mContext, Class);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setAction(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                BaseApplication.mContext.startActivity(intent);
                break;
            }
        }
    }

    /**
     * 判断app是否在后台
     */
    public static boolean isAppIsInBackground() {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) BaseApplication.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        if (runningProcesses == null) {
            return true;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(BaseApplication.mContext.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        }
        return isInBackground;
    }
}