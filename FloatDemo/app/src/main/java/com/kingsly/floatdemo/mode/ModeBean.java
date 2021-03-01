package com.kingsly.floatdemo.mode;

import java.io.Serializable;

/**
 * @author : kingsly
 * @date : On 2021/3/1
 */
public class ModeBean implements Serializable {

    public String title;

    public ModeBean(String title) {
        this.title = title;
    }
}