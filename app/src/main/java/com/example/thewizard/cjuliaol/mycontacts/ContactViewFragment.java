package com.example.thewizard.cjuliaol.mycontacts;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactViewFragment extends Fragment {


    private static final String TAG = "ContactViewFragment";
    private int mColor;
    private Contact mContact;
    private int mPosition;
    private TextView mContactName;
    private FieldsAdapter mAdapter;

    public ContactViewFragment() {
        // Required empty public constructor
    }


    public void setPosition(int position) {
        mPosition = position;

        if (mAdapter != null) {
            mContact = ContactList.getInstance().get(position);
            mAdapter.setContact(mContact);
            updateUI();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_view, container, false);


        // Use this to indicate the ratio
        /*Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;

        // This indicate ratio based on Material design
        RelativeLayout headerSection = (RelativeLayout) v.findViewById(R.id.contact_view_header);

        // We are using LinearLayout is because in activity_contact_view the root layout is LinearLayout
        // and this layout contains contact_view_header
        headerSection.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * (9.0 / 16.0))));*/



        mContact = ContactList.getInstance().get(mPosition);

        mContactName = (TextView) v.findViewById(R.id.contact_name);


        Toolbar toolbar = (Toolbar) v.findViewById(R.id.contact_view_toolbar);
        //  setSupportActionBar(toolbar);


        // To catch the click on the menu item edit (icon)
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.contact_view_edit) {
                    Intent intent = new Intent(getActivity(), ContactEditActivity.class);
                    intent.putExtra(ContactEditActivity.EXTRA, mPosition);
                    startActivity(intent);
                    Log.d(TAG, "Edit is clicked");
                }

                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu_contact_view);

        ListView listView = (ListView) v.findViewById(R.id.contact_view_fields);
        mAdapter = new FieldsAdapter(mContact);
        listView.setAdapter(mAdapter);

        // To use this palette we have to add an external library
        // compile "com.android.support:palette-v7:21.0.+"  in build.gradle (Module:app)

        // Palette takes an image, analyzes it and finds the prominent colors.
        // To do this we need a bitmap which is an object that holds image data.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.parrots);
        Palette palette = Palette.generate(bitmap);


        // With our Palette now created, we can get a color from it and use that to change the color of our icons.
        mColor = palette.getDarkVibrantSwatch().getRgb();

        updateUI();
        return v;
    }




    private void updateUI() {
        mContactName.setText(mContact.getName());
        mAdapter.notifyDataSetChanged();

    }

    // Adapter to load data to ListView; this adapter (BaseAdapter) can handle more than one Array. ArrayAdapter Not.
    private class FieldsAdapter extends BaseAdapter {

        ArrayList<String> phoneNumbers;
        ArrayList<String> emails;

        // Constructor to receive data
        FieldsAdapter(Contact contact) {
          this.setContact(contact);
        }

        public void setContact(Contact contact) {
            this.phoneNumbers = contact.phoneNumbers;
            this.emails = contact.emails;
        }

        @Override
        public int getCount() {
            return phoneNumbers.size() + emails.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // In this case getView cannot be access directly because it is an abstract method
            // super.getView(position,convertView,parent);
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);
            }

            String value = (String) getItem(position);
            TextView contactValue = (TextView) convertView.findViewById(R.id.contact_view_field_value);
            contactValue.setText(value);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.field_icon);

            if (isFirst(position)) {
                if (isEmail(position)) {
                    imageView.setImageResource(R.drawable.ic_email);
                } else {
                    imageView.setImageResource(R.drawable.ic_call);
                }
            }

            imageView.setColorFilter(mColor);

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // In the ListView phoneNumbers are the first to display and later emails
            // so we have to handle the absolute position and the relative position
            if (isEmail(position)) {
                return emails.get(position - phoneNumbers.size());
            } else {
                return phoneNumbers.get(position);
            }


        }

        private boolean isEmail(int position) {

            if (position > phoneNumbers.size() - 1) {
                return true;
            } else {
                return false;
            }

        }

        private boolean isFirst(int position) {

            if (position == 0 || position == phoneNumbers.size()) {
                return true;
            }
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        Log.d(TAG, "We're back, baby");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_contact_view, menu);

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
