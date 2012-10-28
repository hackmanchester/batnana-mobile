package com.poblcreative.batnana.rest;

import org.codegist.crest.CRest;
import org.codegist.crest.CRestBuilder;
import org.codegist.crest.config.MethodConfig;
import org.codegist.crest.io.http.HttpClientHttpChannelFactory;

import android.util.Log;

/**
 * 
 * @author Shaun Rowe <shaun@poblcreative.com>
 * 
 */
public class RestClient
{

    public static CRest crest;
    private static String endpoint = "http://172.17.17.47:8080";
    private static final String TAG = "RestClient";

    public static void setup()
    {
        if (crest == null) {
            buildCrest();
        }
    }

    public static void buildCrest()
    {
        Log.i(TAG, "Building CRest");
        CRestBuilder builder = CRest.property(
                MethodConfig.METHOD_CONFIG_DEFAULT_ENDPOINT, endpoint)
                .setHttpChannelFactory(HttpClientHttpChannelFactory.class);

        crest = builder.build();
    }

    public static BatLocationSearchService getBatLocationSearchService()
    {
        setup();
        return crest.build(BatLocationSearchService.class);
    }

}