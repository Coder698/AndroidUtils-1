package cloud.cn.androidlib.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 * Created by Cloud on 2016/4/20.
 */
public class DialogUtils {
    public static Dialog showConfirmDialog(Activity context, String title, String message, DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", no);
        builder.setPositiveButton("确定", yes);
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static ProgressDialog showProgressDialog(Activity context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }
}
