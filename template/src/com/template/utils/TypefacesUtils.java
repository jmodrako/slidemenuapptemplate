package com.template.utils;

import android.graphics.Typeface;
import android.util.Log;
import com.template.TemplateApp;

import java.util.Hashtable;

public class TypefacesUtils {

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(String typefaceName) {
        synchronized (cache) {
            if (!cache.containsKey(typefaceName)) {
                try {
                    cache.put(typefaceName, Typeface.createFromAsset(TemplateApp.instance.getAssets(), "fonts/" + typefaceName));
                } catch (Exception e) {
                    Log.e("Typeface", "Could not get typeface '" + typefaceName + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(typefaceName);
        }
    }

    public synchronized static Hashtable<String, Typeface> getAllTypefaces() {
        return cache;
    }
}


