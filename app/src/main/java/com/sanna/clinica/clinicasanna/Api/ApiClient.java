package com.sanna.clinica.clinicasanna.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by scott on 9/06/2018.
 */

public class ApiClient {


    private static Retrofit retrofit=null;

    public static Retrofit getClient (String baseUrl){
        if (retrofit==null) {
             retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return  retrofit;
    }
}
