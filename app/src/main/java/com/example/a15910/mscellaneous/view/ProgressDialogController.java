package com.example.a15910.mscellaneous.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * progressDialog
 * Created by hu on 2018/4/19.
 */

public class ProgressDialogController {

    private static ProgressDialogController controller;

    public static ProgressDialogController getInstance() {
        return (controller != null ? controller : (controller = new ProgressDialogController()));
    }

    private ProgressDialogController() {

    }

    private ProgressDialog progressDialog;

    public void showProgressDialog(Context context, String msg, final OnCancelListener cancelListener) {
        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(true);
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);//点击dialog外不可取消
            progressDialog.setCancelable(true);//返回键可取消
            //取消监听
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (cancelListener == null)return;
                    cancelListener.onCancelLister();
                }
            });
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelProgressDialog() {
        if (isShowing()) {
            progressDialog.cancel();
            progressDialog.dismiss();
        }
    }

    private Boolean isShowing() {
        return progressDialog != null && progressDialog.isShowing();

    }

    /**
     * 取消监听
     */
    public interface OnCancelListener{
        void onCancelLister();
    }
}
