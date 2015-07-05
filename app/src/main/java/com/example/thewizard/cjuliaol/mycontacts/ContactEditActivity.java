package com.example.thewizard.cjuliaol.mycontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactEditActivity extends AppCompatActivity {
    public static final String EXTRA = "CEA_EXTRA";
    private static final String TAG ="ContactEditActivity" ;
   private Contact mContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

         mContact = (Contact) getIntent().getSerializableExtra(EXTRA);

        Toolbar toolbar =(Toolbar) findViewById(R.id.contact_edit_toolbar);
        toolbar.setTitle(getResources().getString(R.string.edit_contact));
        toolbar.setNavigationIcon(R.drawable.ic_done);
        // ClickListener for the NavigationIcon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Setting updated values to the Contact
                EditText contactName = (EditText) findViewById(R.id.contact_edit_name);
                mContact.setName(contactName.getText().toString());
                mContact.phoneNumbers = getSectionValues(R.id.phonenumber_section);
                mContact.emails = getSectionValues(R.id.email_section);
                Toast.makeText(ContactEditActivity.this, "Saved contact", Toast.LENGTH_SHORT).show();
                
                finish(); // Finish the activity
            }
        });

        EditText contactEditName = (EditText) findViewById(R.id.contact_edit_name);
        contactEditName.setText(mContact.getName());

        // Adding UI Objects in Code
        addToSection(R.id.phonenumber_section, mContact.phoneNumbers);
        addToSection(R.id.email_section, mContact.emails);

        TextView addNewPhonenumber = (TextView) findViewById(R.id.add_new_phonenumber);
        TextView addNewEmail = (TextView) findViewById(R.id.add_new_email);

        addNewPhonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             addToSection(R.id.phonenumber_section,"");
            }
        });

        addNewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSection(R.id.email_section,"");
            }
        });

    }

    private ArrayList<String> getSectionValues(int sectionID) {
        ArrayList<String> values = new ArrayList<String>();
        LinearLayout section = (LinearLayout) findViewById(sectionID);

        // Looking for every child in the LinearLayout
        for (int i=0; i <section.getChildCount(); i++ ) {
           EditText editText = (EditText) section.getChildAt(i);
            values.add(editText.getText().toString());
        }

        return values;
    }

    private  void addToSection(int sectionID, String value) {
        LinearLayout section = (LinearLayout) findViewById(sectionID);
        EditText editText = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);
        editText.setText(value);
        section.addView(editText);
    }
    private void addToSection(int sectionID, ArrayList<String> values) {

        LinearLayout section = (LinearLayout) findViewById(sectionID);

        // Adding UI Objects in Code
        for (int i = 0; i < values.size(); i++) {
            EditText editText = new EditText(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(layoutParams);
            editText.setText(values.get(i));
            section.addView(editText);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_edit, menu);
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
