package com.bzh.sportrecord.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.DrawableRes;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/21 14:24
 */
public class CommonUtil {

    //请求相机
    public static final int REQUEST_CAPTURE = 100;
    //请求相册
    public static final int REQUEST_PICK = 101;
    //请求截图
    public static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    //获取drawable里图片路径
    public static String getResourcesUri(Context context, @DrawableRes int id) {
        Resources resources = context.getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }
    public void jiance(){ //判断线程
        System.out.println("第一个"+String.valueOf(Thread.currentThread() == Looper.getMainLooper().getThread()));
    }
}
