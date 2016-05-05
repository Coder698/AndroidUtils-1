package cloud.cn.applicationtest.engine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.common.util.DensityUtil;

import cloud.cn.androidlib.utils.KeysUtils;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.activity.home.SafeSetup1Activity;

/**
 * Created by Cloud on 2016/4/21.
 */
public class SafeEngine {
    public static void showSavePassDialog(final Activity context) {
        View view = View.inflate(context, R.layout.dialog_enter_password, null);
        final Dialog dialog = showSelfAdaptionDialog(context, view);
        final EditText et_dialog_password = (EditText)view.findViewById(R.id.et_dialog_password);
        final EditText et_dialog_repassword = (EditText)view.findViewById(R.id.et_dialog_repassword);
        Button btn_dialog_confirm = (Button)view.findViewById(R.id.btn_dialog_confirm);
        Button btn_dialog_cancel = (Button)view.findViewById(R.id.btn_dialog_cancel);
        btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_dialog_password.getText().toString();
                String repassword = et_dialog_repassword.getText().toString();
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(repassword)) {
                    Toast.makeText(context, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                String md5Pass = KeysUtils.encodeMD5(password);
                PrefUtils.putString(AppConstants.PREF.SAFE_PASSWORD, md5Pass);
                dialog.dismiss();
                showEnterPassDialog(context);
            }
        });
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 创建自适应宽度的dialog
     * @param context
     * @param view
     * @return
     */
    private static Dialog showSelfAdaptionDialog(Activity context, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        Dialog dialog = builder.create();
        int width = DensityUtil.getScreenWidth();
        dialog.show();
        //设置对话框宽度,需要在dialog show之后才会起作用
        dialog.getWindow().setLayout((int)(width * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    public static void showEnterPassDialog(final Activity context) {
        View view = View.inflate(context, R.layout.dialog_enter_password, null);
        final Dialog dialog = showSelfAdaptionDialog(context, view);
        final EditText et_dialog_password = (EditText)view.findViewById(R.id.et_dialog_password);
        final EditText et_dialog_repassword = (EditText)view.findViewById(R.id.et_dialog_repassword);
        Button btn_dialog_confirm = (Button)view.findViewById(R.id.btn_dialog_confirm);
        Button btn_dialog_cancel = (Button)view.findViewById(R.id.btn_dialog_cancel);
        et_dialog_repassword.setVisibility(View.GONE);
        btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enterPass = KeysUtils.encodeMD5(et_dialog_password.getText().toString());
                String originPass = PrefUtils.getString(AppConstants.PREF.SAFE_PASSWORD, "");
                if(originPass.equals(enterPass)) {
                    dialog.dismiss();
                    Intent intent = new Intent(context, SafeSetup1Activity.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
