package com.example.thewizard.cjuliaol.mycontacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity implements ContactListFragment.Contract,ContactViewFragment.Contract {

    private ContactListFragment mContactListFragment;
    private ContactViewFragment mContactViewFragment;


    @Override
    public void selectedPosition(int position) {

        if (mContactViewFragment == null) {
            Intent intent = new Intent(this, ContactViewActivity.class);
            intent.putExtra(ContactViewActivity.EXTRA, position);
            startActivity(intent);
        } else
        {
            mContactViewFragment.setPosition(position);
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mContactListFragment = (ContactListFragment) getFragmentManager().findFragmentById(R.id.list_fragment_container);

        if (mContactListFragment == null) {
            mContactListFragment = new ContactListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.list_fragment_container, mContactListFragment)
                    .commit();
          }
        mContactViewFragment = (ContactViewFragment) getFragmentManager().findFragmentById(R.id.view_fragment_container);
        if (mContactViewFragment == null && findViewById(R.id.view_fragment_container) !=null) {
            mContactViewFragment = new ContactViewFragment();
            mContactViewFragment.setPosition(0);
            getFragmentManager().beginTransaction()
                    .add(R.id.view_fragment_container, mContactViewFragment)
                    .commit();
        }




    }


    @Override
    public void selectedEditPosition(int position) {
                     Intent intent = new Intent(this, ContactEditActivity.class);
                    intent.putExtra(ContactEditActivity.EXTRA, position);
                    startActivity(intent);
    }
}
