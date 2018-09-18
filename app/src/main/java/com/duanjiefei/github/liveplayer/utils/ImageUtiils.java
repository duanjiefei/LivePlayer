package com.duanjiefei.github.liveplayer.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.duanjiefei.github.liveplayer.R;

public class ImageUtiils {

    private static final float VER_POSTER_RATIO = 0.73f;
    private static final float HOR_POSTER_RATIO = 1.5f;
    public static void  disPlayImage(ImageView imageView ,String url){
        if (imageView!=null&&url!=null){
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }
    }

    public static void disPlayImage(ImageView imageView,String url,int width,int height){
        if(imageView!= null && url!=null && width > 0 && height>0){
            if (width>height){
                Glide.with(imageView.getContext())
                        .load(url)  //加载图片的url
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                        .error(R.drawable.ic_loading_hor)//加载错误时的图片
                        .fitCenter()//设置图片居中
                        .override(height,width)//重写宽高
                        .into(imageView);//
            }else {
                Glide.with(imageView.getContext())
                        .load(url)  //加载图片的url
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                        .error(R.drawable.ic_loading_hor)//加载错误时的图片
                        .fitCenter()//设置图片居中
                        .override(width,height)//重写宽高
                        .into(imageView);//
            }
        }
    }

    /**
     *   得到合适的海报图的宽高
     * @param context
     * @param columns
     * @return
     */
    public static Point getVerPostSize(Context context,int columns){
        int width =  getScreenWidthPix(context)/columns;
        width =  width -(int) context.getResources().getDimension(R.dimen.dimen_8dp);
        int height =  Math.round(width/VER_POSTER_RATIO);
        Point point = new Point();
        point.x = width;
        point.y = height;
        return point;
    }


    public static Point getHorPostSize(Context context,int columns){
        int width =  getScreenWidthPix(context)/columns;
        width =  width -(int) context.getResources().getDimension(R.dimen.dimen_8dp);
        int height =  Math.round(width/HOR_POSTER_RATIO);
        Point point = new Point();
        point.x = width;
        point.y = height;
        return point;
    }

    public static int  getScreenWidthPix(Context context){
        WindowManager wm  = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        return width;
    }


}
