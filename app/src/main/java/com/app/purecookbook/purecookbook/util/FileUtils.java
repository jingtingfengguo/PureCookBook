package com.app.purecookbook.purecookbook.util;

import android.content.Context;
import android.os.Environment;

/**
 * 文件管理工具 缓存地址工具
 */
public class FileUtils {

    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
