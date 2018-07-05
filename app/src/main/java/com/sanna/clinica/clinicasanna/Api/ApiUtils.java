package com.sanna.clinica.clinicasanna.Api;

/**
 * Created by scott on 9/06/2018.
 */

public class ApiUtils {
    public static  final String BASE_URL="http://webapisanna20180614044954.azurewebsites.net/api/";
    public  static UserService getUserService(){
        return  ApiClient.getClient(BASE_URL).create(UserService.class);
    }
    public  static IMapaService getMapaService(){
        return  ApiClient.getClient(BASE_URL).create(IMapaService.class);
    }

}
