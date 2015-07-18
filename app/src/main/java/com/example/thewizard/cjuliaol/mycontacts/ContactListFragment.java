package com.example.thewizard.cjuliaol.mycontacts;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends ContractFragment<ContactListFragment.Contract> {
    //private ArrayList<Contact> mContacts;
    private ContactList mContacts;
    private ContactAdapter mAdapter;

    public ContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);

        //mContacts = new ArrayList<Contact>();
        mContacts = ContactList.getInstance();

        for (int i = 0; i < 25; i++) {
            Contact contact1 = new Contact();
            contact1.setName("John Smith" + i);
            contact1.phoneNumbers = new ArrayList<String>();
            contact1.phoneNumbers.add("8495557777");
            contact1.phoneNumbers.add("8495554444");
            contact1.emails = new ArrayList<String>();
            contact1.emails.add("john@smith.com");
            contact1.emails.add("captain@smith.com");
            mContacts.add(contact1);
        }

        ListView listView = (ListView) v.findViewById(R.id.contact_list_view);
        mAdapter = new ContactAdapter(mContacts);
        listView.setAdapter(mAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                android.support.v7.app.ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

                // To Hide ActionBar when you scroll down
                if (firstVisibleItem > previousFirstItem) {
                    ab.hide();
                } else if (firstVisibleItem < previousFirstItem) {
                    ab.show();
                }

                previousFirstItem = firstVisibleItem;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /////////////////////

                if (getContrat() != null) {
                    getContrat().selectedPosition(position);
                }


            }
        });

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }


    private void updateUI() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ContactAdapter extends ArrayAdapter<Contact> {

        ContactAdapter(ArrayList<Contact> contacts) {
            super(getActivity(), R.layout.contact_list_row, R.id.contact_row_name, contacts);
        }

        // To customize the custon row
        // To show what you want to show
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Contact contact = getItem(position);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.contact_row_name);
            nameTextView.setText(contact.getName());

            return convertView;
        }
    }


    public interface Contract {
        public void selectedPosition(int position);
    }
    /////////////////////////////
}
