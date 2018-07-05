package com.sanna.clinica.clinicasanna.Api;

/**
 * Created by scott on 18/06/2018.
 */

public class Common {
    public static  final String baseURL = "https://googleapis.com";
    public  static IGoogleApi getGoogleApi()
    {
        return RetrofitClient.getClient(baseURL).create(IGoogleApi.class);
    }
}
