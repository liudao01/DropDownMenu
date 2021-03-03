package com.heiko.dropwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * @Description PopupWindow的一个封装工具类
 * Created by EthanCo on 2016/3/30.
 */
public class DropPopupUtil {
    private static final String TAG = "DropPopupUtil";

    /**
     * @param activity
     * @param contentView 自定义的view
     * @param heightScale 高度比例 0-1
     * @param anchor      锚
     * @param xoff
     * @param yoff
     */
    public static PopupWindow showAsDropDown(Activity activity, View contentView, float heightScale, View anchor, int xoff, int yoff) {
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

        int DaoHangHeight = 0;
        if (isNavigationBarShow(activity)) {
            DaoHangHeight = getDaoHangHeight(activity);
        }
        Log.d(TAG, "showAsDropDown: DaoHangHeight = " + DaoHangHeight);

        int barHeight = 0;
        barHeight = getStatusBarHeight(activity);

        Log.d(TAG, "showAsDropDown: barHeight = " + barHeight);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, allHeight , true); //(int) (mScreenHeight * heightScale)
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
}
