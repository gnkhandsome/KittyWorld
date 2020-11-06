package com.kitty.kitty_base.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;

import com.kitty.kitty_base.R;
import com.kitty.kitty_base.base.BaseActivity;
import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.fragmentdialog.UpdateVersionDialog;
import com.kitty.kitty_base.http.HttpUtils;
import com.kitty.kitty_base.http.base.BaseResponse;
import com.kitty.kitty_base.http.download.CheckApkExistUtil;
import com.kitty.kitty_base.http.download.DownLoadManager;
import com.kitty.kitty_base.http.download.ProgressCallBack;
import com.kitty.kitty_base.interfaces.IResponse;
import com.kitty.kitty_base.model.UpdateInfo;

import java.io.File;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class VersionUtil {

    /**
     * 请求版本更新接口
     */
    public static void updateVersion(BaseActivity activity) {
        try {
            HttpUtils.getUpdateInfo(new IResponse<UpdateInfo>() {
                @Override
                public void onSuccess(BaseResponse<UpdateInfo> baseResponse) {
                    if (baseResponse.code == 0) {
                        String currentVersion = AppApplicationMgr.getVersionName(Utils.getContext());
                        int result = compareVersion(currentVersion, baseResponse.data.getVersion());
                        if (result == -1) {
                            update(baseResponse.data, activity);
                        }
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                }
            });

        } catch (Exception e) {
        }
    }

    private static void update(UpdateInfo data, BaseActivity activity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.UPDATE_INFO, data);
        UpdateVersionDialog updateVersionDialog = new UpdateVersionDialog();
        updateVersionDialog.setArguments(bundle);
        updateVersionDialog.show(activity.getSupportFragmentManager(), "updateVersionDialog");
//        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(activity);
//        normalDialog.setTitle(Utils.getString(R.string.update_title) + " V" + data.getVersion());
//        normalDialog.setCancelable(false);
//        normalDialog.setMessage(data.getInfo());
//        normalDialog.setPositiveButton(R.string.update_btn_text, (dialog, which) -> {
//            downFile(data.getDownload_url(), activity);
//        });
//        if (data.getIs_force() == 0) {
//            normalDialog.setNegativeButton(R.string.not_upgrade_text, (dialog, which) -> dialog.dismiss());
//        }
//        normalDialog.show();
    }


    /**
     * 下载apk文件
     *
     * @param url
     * @param activity
     */
    public static void downFile(String url, Activity activity) {
        String diskDir = getDiskCacheDir(activity);
        if (TextUtils.isEmpty(diskDir)) {
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(Utils.getString(R.string.downloading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        DownLoadManager.getInstance().load(url, new ProgressCallBack<ResponseBody>(diskDir, getDestFileName()) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                installApkO(activity, getDestFileDir(activity));
                Log.i("downFileSuccess", getDestFileDir(activity));
            }

            @Override
            public void progress(final long progress, final long total) {
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) progress);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastUtils.showShort(Utils.getString(R.string.download_failed));
                progressDialog.dismiss();
                CheckApkExistUtil.checkApkExist(activity, activity.getPackageName());
            }
        });
    }

    public static void installApkO(Activity context, String downloadApkPath) {
        // 8.0 需要判断是否允许了安装未知来源应用的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //是否有安装位置来源的权限
            boolean haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {
                installApk(context, downloadApkPath);
            } else {
                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
                normalDialog.setTitle(R.string.requests_permissions_title);
                normalDialog.setMessage(R.string.unknown_source_request_tips);
                normalDialog.setPositiveButton(R.string.to_setting,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Uri packageUri = Uri.parse("package:" + context.getPackageName());
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
                                context.startActivityForResult(intent, 10086);
                            }
                        });
                normalDialog.setNegativeButton(R.string.cancel_text,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                normalDialog.show();
            }
        } else {
            installApk(context, downloadApkPath);
        }
    }

    private static void installApk(Context context, String downloadApk) {
        try {
            File file = new File(downloadApk);
            if (!file.exists()) {
                ToastUtils.showLong(R.string.package_not_exist);
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String type = getMimeType(file);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //7.0 及以上
                Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, type);
            } else {
                //6.0 及以下
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, type);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showShortCenter(R.string.install_failed);
            CheckApkExistUtil.checkApkExist(context, context.getPackageName());
        }
    }

    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return 0代表相等，1代表version1大于version2，-1代表version1小于version2
     */
    private static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 获取
     *
     * @param context
     * @return
     */
    private static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = Objects.requireNonNull(context.getExternalCacheDir()).getPath();
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                CheckApkExistUtil.checkApkExist(context, context.getPackageName());
                return null;
            }
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    private static String getSuffix(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        String fileName = file.getName();
        if (fileName.equals("") || fileName.endsWith(".")) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(index + 1).toLowerCase(Locale.US);
        } else {
            return null;
        }
    }

    private static String getMimeType(File file) {
        String suffix = getSuffix(file);
        if (suffix == null) {
            return "file/*";
        }
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
        if (type != null || !type.isEmpty()) {
            return type;
        }
        return "file/*";
    }

    /**
     * @param context
     * @return
     */
    public static String getDestFileDir(Context context) {
        final String destFileDir = getDiskCacheDir(context);
        return destFileDir + getDestFileName();
    }

    private static String getDestFileName() {
        return "/kittyworld.apk";
    }
}
