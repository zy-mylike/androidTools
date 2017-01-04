package com.enetic.push.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.enetic.esale.R;
import com.enetic.push.adapter.ContactAdapter;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;


/**
 * 通讯录朋友
 */
@Layout(R.layout.activity_contacts)
public class ContactsActivity extends ParentActivity {
    private final static String TITLE = "通讯录朋友";
    @ViewIn(R.id.contact_list)
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        mListView.setAdapter(new ContactAdapter(this, null));
    }
}