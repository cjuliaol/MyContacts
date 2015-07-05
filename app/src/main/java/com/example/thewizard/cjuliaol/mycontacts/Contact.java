package com.example.thewizard.cjuliaol.mycontacts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cjuliaol on 30/06/2015.
 */
public class Contact implements Serializable {

    private String mName;

    public ArrayList<String> emails;

    public ArrayList<String> phoneNumbers;

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
