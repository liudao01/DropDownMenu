package com.heiko.dropwidget;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.lang.reflect.Method;

/**
 * @Description PopupWindow的一个封装工具类
 * Created by EthanCo on 2016/3/30.
 */
public class DropPopupUtilBackup {
    private static final String TAG = "DropPopupUtil";

    /**
     * @param activity
     * @param contentView 自定义的view
     * @param heightScale 高度比例 0-1
     * @param anchor      锚
     * @param xoff
     * @param yoff
     */
    public static PopupWindow showAsDropDownCustom(Activity activity, View contentView, float heightScale, View anchor, int xoff, int yoff) {

        int DaoHangHeight = 0;
        boolean navigationBarShowing = false;
        if (isNavigationBarShowing(activity)) {
            navigationBarShowing = true;
            Log.d(TAG, "展示了底部导航栏");
            DaoHangHeight = getDaoHangHeight(activity);
        } else {
            navigationBarShowing = false;
            Log.d(TAG, "没有底部导航栏");
            DaoHangHeight = 0;
        }
        Log.d(TAG, "导航栏showAsDropDown: DaoHangHeight = " + DaoHangHeight);
        Log.d(TAG, "底部导航栏showAsDropDown: getNavHeight = " + getNavHeight(activity));

        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int mScreenHeight = outMetrics.heightPixels;
        float anchorHeight = mScreenHeight - anchor.getRotationY();
        Log.d(TAG, "showAsDropDown: mScreenHeight =" + mScreenHeight);
        Log.d(TAG, "showAsDropDown: anchor.getRotationY() =" + anchor.getRotationY());
        Log.d(TAG, "showAsDropDown: anchorHeight =" + anchorHeight);
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        Log.d(TAG, location[0] + "  " + location[1]);
        final int height = anchor.getHeight();

        Log.d(TAG, " location[1] = " + location[1]);
        Log.d(TAG, "anchor.getHeight() height = " + height);
        int allHeight = mScreenHeight - height;//总体高度
        int barHeight = 0;
        barHeight = getStatusBarHeight(activity);
        allHeight = allHeight - DaoHangHeight-50;

        final View viewById = contentView.findViewById(R.id.ll_other);

        Log.d(TAG, "showAsDropDown: barHeight = " + barHeight);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, allHeight , true);


        popupWindow.setTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.showAsDropDown(anchor, xoff, yoff);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    public static PopupWindow showAsDropDown(Activity activity, View contentView, float heightScale, View anchor, int xoff, int yoff) {

        int DaoHangHeight = 0;
        boolean navigationBarShowing = false;
        if (isNavigationBarShowing(activity)) {
            navigationBarShowing = true;
            Log.d(TAG, "展示了底部导航栏");
            DaoHangHeight = getDaoHangHeight(activity);
        } else {
            navigationBarShowing = false;
            Log.d(TAG, "没有底部导航栏");
            DaoHangHeight = 0;
        }
        Log.d(TAG, "导航栏showAsDropDown: DaoHangHeight = " + DaoHangHeight);
        Log.d(TAG, "底部导航栏showAsDropDown: getNavHeight = " + getNavHeight(activity));

        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int mScreenHeight = outMetrics.heightPixels;
        float anchorHeight = mScreenHeight - anchor.getRotationY();
        Log.d(TAG, "showAsDropDown: mScreenHeight =" + mScreenHeight);
        Log.d(TAG, "showAsDropDown: anchorHeight =" + anchorHeight);
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        Log.d(TAG, location[0] + "  " + location[1]);
        final int height = anchor.getHeight();

        Log.d(TAG, " location[1] = " + location[1]);
        Log.d(TAG, "anchor.getHeight() height = " + height);

        int allHeight = mScreenHeight - location[1];//总体高度
        if (navigationBarShowing) {
            allHeight = allHeight - height;
        }

//        int allHeight = mScreenHeight - location[1] - height;//总体高度

        final View viewById = contentView.findViewById(R.id.ll_other);

//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewById.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
//        int afterHeight = (int) (allHeight * heightScale);
//        linearParams.height = afterHeight;
//        viewById.setLayoutParams(linearParams);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, allHeight, true); //(int) (mScreenHeight * heightScale)
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.MATCH_PARENT, allHeight + getStatusBarHeight(activity), true); //(int) (mScreenHeight * heightScale)
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.MATCH_PARENT, (int) (mScreenHeight * heightScale), true); //(int) (mScreenHeight * heightScale)
//        popupWindow.setHeight(mScreenHeight);
        popupWindow.setTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.showAsDropDown(anchor, xoff, yoff);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    private static boolean checkDeviceHasNavigationBar(Activity context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }
    private static int getStatusBarHeight(Activity context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public static boolean isNavigationBarShowing(Activity context) {
        //判断手机底部是否支持导航栏显示
        boolean haveNavigationBar = checkDeviceHasNavigationBar(context);
        if (haveNavigationBar) {
            if (Build.VERSION.SDK_INT >= 17) {
                String brand = Build.BRAND;
                String mDeviceInfo;
                if (brand.equalsIgnoreCase("HUAWEI")) {
                    mDeviceInfo = "navigationbar_is_min";
                } else if (brand.equalsIgnoreCase("XIAOMI")) {
                    mDeviceInfo = "force_fsg_nav_bar";
                } else if (brand.equalsIgnoreCase("VIVO")) {
                    mDeviceInfo = "navigation_gesture_on";
                } else if (brand.equalsIgnoreCase("OPPO")) {
                    mDeviceInfo = "navigation_gesture_on";
                } else {
                    mDeviceInfo = "navigationbar_is_min";
                }

                if (Settings.Global.getInt(context.getContentResolver(), mDeviceInfo, 0) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getNavHeight(Activity context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //判断底部导航栏是否为显示状态
            boolean navigationBarShowing = isNavigationBarShowing(context);
            if (navigationBarShowing) {
                int height = resources.getDimensionPixelSize(resourceId);
                return height;
            }
        }
        return 0;
    }


    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getDaoHangHeight(Activity context) {
        int result = 0;
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(resourceId);
            return dimensionPixelSize;
        } else
            return 0;
    }


    /**
     * @param activity
     * @param contentView 自定义的view
     * @param heightScale 高度比例 0-1
     * @param anchor
     */
    public static PopupWindow showAsDropDown(Activity activity, View contentView, float heightScale, View anchor) {
        return showAsDropDown(activity, contentView, heightScale, anchor, 0, 0);
    }

    /**
     * @param activity
     * @param contentView 自定义的view
     * @param heightScale 高度比例 0-1
     * @param anchor
     */
    public static PopupWindow showAsDropDownCustom(Activity activity, View contentView, float heightScale, View anchor) {
        return showAsDropDownCustom(activity, contentView, heightScale, anchor, 0, 0);
    }
}
