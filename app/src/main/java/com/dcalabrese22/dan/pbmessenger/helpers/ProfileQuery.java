package com.dcalabrese22.dan.pbmessenger.helpers;

import android.provider.Contacts;
import android.provider.ContactsContract;

/**
 * Created by dan on 9/27/17.
 */

public class ProfileQuery {

    public static final String[] PROJECTION = {ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY};


    public static final String SELECTION = ContactsContract.Contacts.Data.MIMETYPE +
            " = ?";

    public static final String[] ARGS = new String[]{ContactsContract.CommonDataKinds.Email
            .CONTENT_ITEM_TYPE};

    public static final String SORT = ContactsContract.Contacts.Data.IS_PRIMARY + " DESC";


}
