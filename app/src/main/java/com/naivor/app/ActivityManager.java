package com.naivor.app;

import com.naivor.app.presentation.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ActivityManager app的activity的管理类
 *
 * Created by tianlai on 16-3-7.
 */
public class ActivityManager {

    //Activity 管理
    private  List<BaseActivity> activities = new ArrayList<>();

    public ActivityManager() {
        this.activities = new ArrayList<>();
    }

    /**
     * 将activity加入列表
     *
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        if (activity!=null){
            activities.add(activity);
        }
    }

    /**
     * 将activity移除列表
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    /**
     * 退出程序
     *
     */
    public void ExitApplication() {
        for (BaseActivity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }

        // 杀死进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
