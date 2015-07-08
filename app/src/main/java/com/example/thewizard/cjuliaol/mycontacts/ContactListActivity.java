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

public class ContactListActivity extends AppCompatActivity {

    //private ArrayList<Contact> mContacts;
    private ContactList mContacts;
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

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

        ListView listView = (ListView) findViewById(R.id.contact_list_view);
        mAdapter = new ContactAdapter(mContacts);
        listView.setAdapter(mAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // To Hide ActionBar when you scroll down
                if (firstVisibleItem > previousFirstItem) {
                    getSupportActionBar().hide();
                } else if (firstVisibleItem < previousFirstItem) {
                    getSupportActionBar().show();
                }

                previousFirstItem = firstVisibleItem;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ContactListActivity.this, ContactViewActivity.class);
                intent.putExtra(ContactViewActivity.EXTRA, position);

                startActivity(intent);

            }
        });

    }


    private class ContactAdapter extends ArrayAdapter<Contact> {

        ContactAdapter(ArrayList<Contact> contacts) {
            super(ContactListActivity.this, R.layout.contact_list_row, R.id.contact_row_name, contacts);
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

    @Override
    protected void onResume() {
        super.onResume();

        updateUI();
    }

    private void updateUI() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
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
}
