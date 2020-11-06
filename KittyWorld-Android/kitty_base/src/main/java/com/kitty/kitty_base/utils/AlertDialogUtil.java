package com.kitty.kitty_base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kitty.kitty_base.R;

public class AlertDialogUtil {


    public static void showPermissionDialog(Activity topActivity, String title, String content, int type) {
        try {
            Dialog dialog = new Dialog(topActivity, R.style.ActionSheetDialogStyle);
            LayoutInflater inflater = (LayoutInflater) topActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View menuView = inflater.inflate(R.layout.stopserver_dialog_layout, null);
            dialog.setContentView(menuView);
            TextView tv_tip_title = menuView.findViewById(R.id.tv_tip_title);
            TextView tv_tip_content = menuView.findViewById(R.id.tv_tip_content);
            TextView tv_tip_dismiss = menuView.findViewById(R.id.tv_tip_dismiss);
            tv_tip_title.setText(title);
            tv_tip_content.setText(content);
            tv_tip_dismiss.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
            tv_tip_dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            Window dialogWindow = dialog.getWindow();
            assert dialogWindow != null;
            dialogWindow.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
            dialog.show();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
