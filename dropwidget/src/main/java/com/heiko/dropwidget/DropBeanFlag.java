package com.heiko.dropwidget;

/**
 * @author EthanCo
 * @since 2018/5/17
 */

public interface DropBeanFlag {
    String getDropName();

    Boolean isDropChecked();

    void setDropChecked(Boolean checked);

    int getDropCheckedImageRes();
    int getNoneDropCheckedImageRes();

    int getSelectTextColor();
    int getNoneTextColor();

    //未读红色小点数字
    int getRedNumberPoint();


}
