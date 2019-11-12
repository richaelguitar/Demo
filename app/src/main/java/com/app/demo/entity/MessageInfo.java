package com.app.demo.entity;

import java.util.HashMap;
import java.util.Map;

public class MessageInfo {

    private Map<String,String>  values = new HashMap<>();

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
