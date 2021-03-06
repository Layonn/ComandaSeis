package br.com.comandaseis.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * Classe responsável por cuidar do cache das imagens*/

public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache{
    public static int getDeafultLruCacheSize(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    public LruBitmapCache(){
        this(getDeafultLruCacheSize());
    }

    public LruBitmapCache(int sizeInKiloBytes){
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value){
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url){
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap){
        put(url, bitmap);
    }


}
