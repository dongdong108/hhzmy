package com.hhzmy.tools;

import android.content.Context;
import android.graphics.Bitmap;

import com.bw.honghaizidemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by wangkuan on 2016/11/8.
 */
public class ImageLoaderUtils {

    /**
     * 初始化ImageLoaderConfiguration 这个可以只做简单的初始化,此方法建议在
     * Application中进行初始化
     *
     * @param context
     */
    public static void initConfiguration(Context context) {

        ImageLoaderConfiguration.Builder configuration = new ImageLoaderConfiguration.Builder(context);
        ImageLoader.getInstance().init(configuration.build());
    }

    /**
     * 初始化DisplayImageOptions
     *
     * @param
     * @return
     */
    public static DisplayImageOptions initOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)


//--------------------------------------------------------------------
//如果您只想简单使用ImageLoader这块也可以不用配置
                // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型//
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 设置图片的解码配置
                // .decodingOptions(options)
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                // 设置图片在下载前是否重置，复位
                .resetViewBeforeLoading(true)
                // 是否设置为圆角，弧度为多少
                .displayer(new RoundedBitmapDisplayer(20))
                // 是否图片加载好后渐入的动画时间
                .displayer(new FadeInBitmapDisplayer(100))
                // 构建完成
//-------------------------------------------------------------------

                .build();
        return options;
    }

}
