package com.example.venusaur;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

class MySingleton {

    private static MySingleton ourInstance;

    private static Context mContext;

    private RequestQueue mRequestQue;

    private ImageLoader mImageLoader;

    private MySingleton(Context context) {
        mContext = context;
        mRequestQue = getRequestQue();
        mImageLoader = new ImageLoader(mRequestQue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    static synchronized MySingleton getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new MySingleton(context);
        }
        return ourInstance;
    }

    public RequestQueue getRequestQue() {
        if (mRequestQue == null) {
            mRequestQue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQue;
    }

    public <T> void addToRequestQue(Request<T> request) {
        getRequestQue().add(request);
    }

    public ImageLoader getmImageLoader(){
        return mImageLoader;
    }
}
