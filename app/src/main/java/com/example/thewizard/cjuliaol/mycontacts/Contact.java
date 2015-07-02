package com.example.thewizard.cjuliaol.mycontacts;

import java.io.Serializable;

/**
 * Created by cjuliaol on 30/06/2015.
 */
public class Contact implements Serializable {

    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
