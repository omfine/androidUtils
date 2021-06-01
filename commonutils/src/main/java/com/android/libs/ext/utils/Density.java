package com.android.libs.ext.utils;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

public class Density {

    private static final float WIDTH = 160; //参考设备的宽，单位dp

    private static float appDensity; //表示屏幕密度

    private static float appScaledDensity;//表示字体缩放比例,默认与appDensity相等


    public static void setDensity(final Application application, Activity activity) {

        //获取当前屏幕的显示信息

        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();

        if (appDensity == 0) {

            //初始化赋值

            appDensity = displayMetrics.density;

            appScaledDensity = displayMetrics.scaledDensity;



            //添加字体变化监听

/*            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {

                    if (newConfig != null && newConfig.fontScale > 0) { //说明字体大小改变了

                        //重新赋值appScaledDensity

                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;

                    }
                }

                @Override
                public void onLowMemory() {

                }

            });*/
        }

        //计算目标值density、scaledDensity、densityDpi

        float targetDensity = displayMetrics.widthPixels / WIDTH;
//        float targetDensity = 160f;

        float targetScaledDensity = targetDensity * (appDensity / appScaledDensity);

        int targetDensityDpi = (int) (targetDensity * 160);

        //替换activity的density、scaledDensity、densityDpi

        DisplayMetrics dm = activity.getResources().getDisplayMetrics();

        ILog.e("==targetDensity: " + targetDensity + "   targetScaledDensity: " + targetScaledDensity + "   targetDensityDpi: " + targetDensityDpi);
        ILog.e("==targetDensity22appDensity: " + appDensity + "   appScaledDensity: " + appScaledDensity + "   targetDensityDpi: " + targetDensityDpi);

/*
        dm.density = targetDensity;

        dm.scaledDensity = targetScaledDensity;

        dm.densityDpi = targetDensityDpi;*/

        dm.density = 1;

        dm.scaledDensity = 1;

        dm.densityDpi = 160;

    }

}
