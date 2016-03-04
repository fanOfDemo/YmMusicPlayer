package com.yw.musicplayer.util;

/**
 * Created by wengyiming on 2016/3/4.
 */
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;

public class BundlerResourceLoader {

    private static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            try {
                AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                        assetManager, apkPath);
            } catch (Throwable th) {
                System.out.println("debug:createAssetManager :"+th.getMessage());
                th.printStackTrace();
            }
            return assetManager;
        } catch (Throwable th) {
            System.out.println("debug:createAssetManager :"+th.getMessage());
            th.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Bundle中的资源
     * @param context
     * @return
     */
    public static Resources getBundleResource(Context context){
        AssetsManager.copyAllAssetsApk(context);
        File dir = context.getDir(AssetsManager.APK_DIR, Context.MODE_PRIVATE);
        String apkPath = dir.getAbsolutePath()+"/BundleApk.apk";
        System.out.println("debug:apkPath = "+apkPath+",exists="+(new File(apkPath).exists()));
        AssetManager assetManager = createAssetManager(apkPath);
        return new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
    }

}
