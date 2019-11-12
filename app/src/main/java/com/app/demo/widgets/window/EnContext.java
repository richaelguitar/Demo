package com.app.demo.widgets.window;

import android.app.Application;

import com.app.demo.App;


public class EnContext {

    private static final Application INSTANCE = App.App;


    public static Application get() {
        return INSTANCE;
    }
}
