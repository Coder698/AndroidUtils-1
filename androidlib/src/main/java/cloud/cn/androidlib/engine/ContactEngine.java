package cloud.cn.androidlib.engine;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.SparseArray;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.entity.ContactBean;
import cloud.cn.androidlib.net.SuccessCallback;

/**
 * Created by Cloud on 2016/4/25.
 */
public class ContactEngine {
    public static void listContacts(final SuccessCallback<List<ContactBean>> callback) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; //联系人uri
        // 查询的字段
        String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };
        new AsyncQueryHandler(x.app().getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<ContactBean> contactBeans = new ArrayList<ContactBean>();
                SparseArray<ContactBean> contactIdMap = new SparseArray();
                while(cursor.moveToNext()) {
                    int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
                    String sortKey = cursor.getString(cursor.getColumnIndex("sort_key"));
                    long photoId = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));
                    String lookUpKey = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));

                    if(contactIdMap.get(contactId) == null) {
                        ContactBean contactBean = new ContactBean();
                        contactBean.setContactId(contactId);
                        contactBean.setDisplayName(displayName);
                        contactBean.setPhoneNum(phoneNum);
                        contactBean.setSortKey(sortKey);
                        contactBean.setPhotoId(photoId);
                        contactBean.setLookUpKey(lookUpKey);
                        contactBeans.add(contactBean);
                        contactIdMap.put(contactId, contactBean);
                    } else {
                        //do nothing,号码可能有多个，取第一个
                    }
                }
                cursor.close();
                callback.onSuccess(contactBeans);
            }
        }.startQuery(0, null, uri, projection, null, null, "sort_key COLLATE LOCALIZED asc");
    }
}
