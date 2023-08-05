package com.itu.tourisme.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton singleton;
    private RequestQueue requestQueue;
    private static Context ctx;

    public MySingleton(Context context){
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized  MySingleton getInstance(Context context){
        if(singleton == null){
            singleton = new MySingleton(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public <T>void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
