package com.heiko.dropwidget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.lang.reflect.Method;

/**
 * @Description PopupWindow的一个封装工具类
 * Created by EthanCo on 2016/3/30.
 */
public class DropPopupUtil {
    private static final String TAG = "DropPopupUtil";


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
        Log.d(TAG, "showAsDropDown: 屏幕高度 =" + mScreenHeight);
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        Log.d(TAG, location[0] + "  " + location[1]);
        int anchorHeight = anchor.getHeight();

        Log.d(TAG, " location[1] = " + location[1]);
        Log.d(TAG, "anchor.getHeight() 控件高度 height = " + anchorHeight);
//        int allHeight = mScreenHeight - height;//总体高度
        int barHeight = 0;
        barHeight = getStatusBarHeight(activity);
//        allHeight = allHeight - DaoHangHeight-barHeight;

        int height = activity.getResources().getDisplayMetrics().heightPixels;// 屏幕的高
        Log.d(TAG, "height 屏幕的高height = " + height);
//        int temp = height - getBottomKeyboardHeight(activity);
        Log.d(TAG, "showAsDropDownCustom: getStatusBarHeight(activity)  = " + getStatusBarHeight(activity));
        Log.d(TAG, "showAsDropDownCustom: getBottomKeyboardHeight(activity)(activity)  = " + getBottomKeyboardHeight(activity));
        int temp = height - getBottomKeyboardHeight(activity) - anchorHeight;
//        int temp = height - getStatusBarHeight(activity) - anchorHeight;
//        int temp = height - getStatusBarHeight(activity) - getBottomKeyboardHeight(activity);

        final View viewById = contentView.findViewById(R.id.ll_other);
//        activity.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        Log.d(TAG, "showAsDropDown: barHeight = " + barHeight);
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.MATCH_PARENT, allHeight , true);
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true); //(int) (mScreenHeight * heightScale)
//        popupWindow.setHeight(mScreenHeight);
        final PopupWindow popupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT,
                temp
        );//设置popupWindow的高为屏幕的高+顶部状态栏的高+底部虚拟按键的高

        popupWindow.setTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.showAsDropDown(anchor, xoff, yoff);
        popupWindow.setClippingEnabled(false);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    /**
     * @param activity
     * @param contentView 自定义的view
     * @param heightScale 高度比例 0-1
     * @param anchor      锚
     * @param xoff
     * @param yoff
     */
    public static PopupWindow showAsDropDown(Activity activity, View contentView, float heightScale, View anchor, int xoff, int yoff) {

        int DaoHangHeight = 0;
        if (isNavigationBarShowing(activity)) {

            Log.d(TAG, "展示了底部导航栏");
            DaoHangHeight = getDaoHangHeight(activity);
        } else {
            Log.d(TAG, "没有底部导航栏");
            DaoHangHeight = 0;
        }
        Log.d(TAG, "showAsDropDown: DaoHangHeight = " + DaoHangHeight);

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
//        int allHeight = mScreenHeight - height;//总体高度
//        int allHeight = mScreenHeight - location[1];//总体高度
        int allHeight = mScreenHeight - location[1] - height;//总体高度

        final View viewById = contentView.findViewById(R.id.ll_other);

//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewById.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
//        int afterHeight = (int) (allHeight * heightScale);
//        linearParams.height = afterHeight;
//        viewById.setLayoutParams(linearParams);


        int barHeight = 0;
        barHeight = getStatusBarHeight(activity);

        Log.d(TAG, "showAsDropDown: barHeight = " + barHeight);
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
//        popupWindow.showAsDropDown(anchor, xoff, height);
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
     * 获取底部虚拟键盘的高度
     */
    public static int getBottomKeyboardHeight(Activity context) {
        int screenHeight = getAccurateScreenDpi(context)[1];
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightDifference = screenHeight - dm.heightPixels;
        return heightDifference;
    }

    public static int[] getAccurateScreenDpi(Activity context) {
        int[] screenWH = new int[2];
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class<?> c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            screenWH[0] = dm.widthPixels;
            screenWH[1] = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenWH;
    }

    private static int getStatusBarHeight(Activity context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 判断是否显示虚拟导航栏
     *
     * @return
     */
    public static boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
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
