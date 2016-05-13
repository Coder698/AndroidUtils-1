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
    /**
     * 显示confirm对话框
     */
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

    /**
     * 显示进度条对话框,最大进度100
     * @param context
     * @return
     */
    public static ProgressDialog showProgressDialog(Activity context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    /**
     * 显示单选按钮对话框
     */
    public static Dialog showSingleChoiceDialog(Activity context, String title, int iconRes, String[] items, int defaultVal,
                                                DialogInterface.OnClickListener itemClicked, DialogInterface.OnCancelListener cancel) {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setIcon(iconRes);
        builder.setSingleChoiceItems(items, defaultVal, itemClicked);
        builder.setOnCancelListener(cancel);
        dialog = builder.show();
        return dialog;
    }
}
