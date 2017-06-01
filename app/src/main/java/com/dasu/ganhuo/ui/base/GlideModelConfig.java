package com.dasu.ganhuo.ui.base;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by dasu on 2017/5/4.
 */

public class GlideModelConfig implements GlideModule {

    private int mDiskSize = 1024 * 1024 * 500;  //500M
//    private int mMemorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, mDiskSize));  //内存中
        builder.setDiskCache(new ExternalCacheDiskFactory(context)); //sd卡中

        // 默认内存和图片池大小
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
//        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize)); // 该两句无需设置，是默认的
//        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));

        // 自定义内存和图片池大小
//        builder.setMemoryCache(new LruResourceCache(mMemorySize));
//        builder.setBitmapPool(new LruBitmapPool(mMemorySize));

        // 定义图片格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
