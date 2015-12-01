package br.com.comandaseis.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import br.com.comandaseis.util.LruBitmapCache;

/**
 * Singleton responsável por inicializar os objetos da volley
 *
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();

        if(mImageLoader == null){
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }

        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        //tag padrão se a tag estiver vazia
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }
}
