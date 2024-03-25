/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova.inappbrowser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.window.OnBackInvokedDispatcher;
import android.window.OnBackInvokedCallback;

import org.apache.cordova.LOG;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Oliver on 22/11/2013.
 */
public class InAppBrowserDialog extends Dialog {
    Context context;
    InAppBrowser inAppBrowser = null;
    protected static final String LOG_TAG = "InAppBrowser";

    public InAppBrowserDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            OnBackInvokedCallback callback = new OnBackInvokedCallback() {
                @Override
                public void onBackInvoked() {
                    LOG.d(LOG_TAG, "InAppBrowserDialog onBackInvoked");
                    // Handle the back button event
                    handleBackEvent();
                }
            };

            this.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_OVERLAY,
                    callback);
        }
    }

    public void setInAppBroswer(InAppBrowser browser) {
        this.inAppBrowser = browser;
    }

    public void onBackPressed () {
        LOG.d(LOG_TAG, "InAppBrowserDialog onBackPressed SDK_INT: " + Build.VERSION.SDK_INT + ", TIRAMISU: " + Build.VERSION_CODES.TIRAMISU);
        handleBackEvent();
    }

    private boolean handleBackEvent() {
        LOG.d(LOG_TAG, "InAppBrowserDialog handleBackEvent");
        if (this.inAppBrowser == null) {
            this.dismiss();
            return false;
        } else {
            // better to go through the in inAppBrowser
            // because it does a clean up
            // this.dismiss();
            this.inAppBrowser.hanldeBackbutton();
            return true;
            // if (this.inAppBrowser.hardwareBack() && this.inAppBrowser.canGoBack()) {
            //     this.inAppBrowser.goBack();
            //     return true;
            // }  else {
            //     this.inAppBrowser.closeDialog();
            //     return false;
            // }
        }
    }
}
