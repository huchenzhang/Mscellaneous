package com.example.a15910.mscellaneous.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import com.example.a15910.mscellaneous.R;

/**
 * 简单的弹窗
 * Created by hu on 2018/4/19.
 */

public class AlertDialogController {
    private static AlertDialogController instance = null;

    public static AlertDialogController getInstance() {
        if (instance == null) {
            instance = new AlertDialogController();
        }
        return instance;
    }

    private AlertDialogController(){}

    public void showDialog(Context context,String title ,String content, final AlertDialogController.DialogCallBack callBack) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.positiveButton();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));

    }

    public interface DialogCallBack {
        void positiveButton();
    }

}