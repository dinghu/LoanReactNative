package com.xinyonghua.xyh.reactmodule;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.google.gson.Gson;
import com.xinyonghua.xyh.contacts.ContactsAllBean;
import com.xinyonghua.xyh.contacts.ContactsBean;
import com.xinyonghua.xyh.contacts.ContactsUtils;
import com.xinyonghua.xyh.udcredit.UdCreditAuthListener;
import com.xinyonghua.xyh.udcredit.UdCreditUtils;

import java.util.List;

public class LoanReactContextJavaModule extends ReactContextBaseJavaModule {

    private final int REQUEST_ECODE_PERMISSION_CONTACTS = 100;
    private final int REQUEST_ECODE_CONTACTS = 1000;
    private ReactApplicationContext mContext;
    private Callback mErrorCallback;
    private Callback mSuccessCallback;

    private ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_ECODE_CONTACTS) {
                //读取联系人
                ContactsBean contactsBean = ContactsUtils.getSelectContacts(activity, data);
                List<ContactsBean> contactsBeanList = ContactsUtils.readContacts(activity);
                if (contactsBeanList != null && !contactsBeanList.isEmpty()) {
                    if (mSuccessCallback != null) {
                        ContactsAllBean contactsAllBean = new ContactsAllBean();
                        contactsAllBean.allContact = contactsBeanList;
                        contactsAllBean.pickContact = contactsBean;
                        Gson gson = new Gson();
                        String contactsAllBeanJson = gson.toJson(contactsAllBean);
                        mSuccessCallback.invoke(contactsAllBeanJson);
                    }
                }
            }
        }
    };


    public LoanReactContextJavaModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "LoanOnAndroid";
    }


    // 获取应用包名
    // 要导出一个方法给JavaScript使用，Java方法需要使用注解@ReactMethod
    @ReactMethod
    public void getPackageName() {
        String name = getReactApplicationContext().getPackageName();
        Toast.makeText(getReactApplicationContext(), name, Toast.LENGTH_LONG).show();
    }

    private void goToContact() {
        Activity activity = getCurrentActivity();
        // 跳转到联系人界面
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_ECODE_CONTACTS);
    }

    // android端代码
    @ReactMethod
    public void getContacts(Callback errorCallback, Callback successCallback) {
        try {
            Activity activity = getCurrentActivity();
            if (activity == null) {
                errorCallback.invoke("CurrentActivity is null");
                return;
            }
            // 成功时回调
            this.mErrorCallback = errorCallback;
            this.mSuccessCallback = successCallback;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permission = {Manifest.permission.READ_CONTACTS};
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(permission, REQUEST_ECODE_PERMISSION_CONTACTS);
                } else {
                    goToContact();
                }
            } else {
                goToContact();
            }
        } catch (IllegalViewOperationException e) {
            // 失败时回调
            errorCallback.invoke(e.getMessage());
        }
    }


    //接入盾
    @ReactMethod
    public void doUdCreditAuth(final Callback errorCallback, final Callback successCallback) {
        Activity activity = getCurrentActivity();
        if (activity == null) {
            errorCallback.invoke("CurrentActivity is null");
            return;
        }
        UdCreditUtils.auth(activity, new UdCreditAuthListener() {
            @Override
            public void onResult(String s) {
                successCallback.invoke(s);
            }
        });
    }

}
