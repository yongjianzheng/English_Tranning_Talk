package com.yongjian.english_tranning_talk.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by YONGJIAN on 2016/6/8 0008.
 */
public class ProgressDialogHelper {
    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String message) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    public static void cancelable(Boolean b){
        if (progressDialog!=null)
         progressDialog.setCancelable(b);
    }
}
