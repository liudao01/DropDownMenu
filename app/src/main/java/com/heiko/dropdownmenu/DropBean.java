package com.heiko.dropdownmenu;

import android.graphics.Color;

import com.heiko.dropwidget.DropBeanFlag;

/**
 * DropBean
 *
 * @author EthanCo
 * @since 2018/5/17
 */

public class DropBean implements DropBeanFlag {
    private int id;
    private String name;
    private boolean isChecked;
    private int number;

    public DropBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDropName() {
        return getName();
    }

    @Override
    public Boolean isDropChecked() {
        return isChecked;
    }

    @Override
    public void setDropChecked(Boolean checked) {
        this.isChecked = checked;
    }

    @Override
    public int getDropCheckedImageRes() {
        return R.drawable.drop_ic_tick;
    }

    @Override
    public int getNoneDropCheckedImageRes() {
        return 0;
    }

    @Override
    public int getSelectTextColor() {
        return  0;
//        return  Color.parseColor("#ff1a1a1a");
    }

    @Override
    public int getNoneTextColor() {
        return  0;
//        return  Color.parseColor("#4d1a1a1a");
    }

    @Override
    public int getRedNumberPoint() {
        return number;
    }
}
