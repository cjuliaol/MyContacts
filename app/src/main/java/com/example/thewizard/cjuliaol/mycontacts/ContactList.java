package com.example.thewizard.cjuliaol.mycontacts;

import java.util.ArrayList;

/**
 * Created by cjuliaol on 05/07/2015.
 */
// This is a singleton
public class ContactList extends ArrayList<Contact> {

    public static ContactList sInstance = null;

    private ContactList() {}
    public static ContactList getInstance() {
        if(sInstance == null) {
            sInstance = new ContactList();
        }
        return sInstance;
    }

}
