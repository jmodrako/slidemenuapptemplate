package com.template.utils;

import android.content.Context;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;
import com.jsonrpclib.JsonRpc;
import com.jsonrpclib.JsonRpcFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.template.Configuration;
import com.template.api.VemmaBodyAPI;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 26.07.2013
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
@EBean(scope = Scope.Singleton)
public class Connection {

    @RootContext
    Context context;


    public static JsonRpc rpc;
    public static VemmaBodyAPI api;

    @AfterInject
    void createImageDownloader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .resetViewBeforeLoading(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
        //ImageLoader.getInstance().handleSlowNetwork(true);
    }

    @AfterInject
    void prepareJsonRpc() {
        rpc = JsonRpcFactory.getJsonRpc(context, Configuration.VEMMA_BODE_URL);
        api = rpc.getService(VemmaBodyAPI.class);
    }

}
