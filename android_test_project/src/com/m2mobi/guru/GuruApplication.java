package com.m2mobi.guru;
import android.app.Application;
import android.graphics.Bitmap;
import com.m2mobi.guru.utils.MyLog;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by m2mobi on 2/7/14.
 */
public class GuruApplication extends Application {

    private static final String LOG_TAG = "GuruApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        MyLog.i(LOG_TAG, "----------");
        MyLog.i(LOG_TAG, "onCreate()");
        MyLog.i(LOG_TAG, "----------");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null)
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
        .memoryCacheSize(2 * 1024 * 1024)
        .discCacheSize(50 * 1024 * 1024)
        .discCacheFileCount(100)
        .writeDebugLogs().build();

        ImageLoader.getInstance().init(config);
    }

}
