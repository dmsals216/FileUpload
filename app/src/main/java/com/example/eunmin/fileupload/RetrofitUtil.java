package com.example.eunmin.fileupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eunmin on 2017-12-02.
 */

public class RetrofitUtil {
    private static String SERVER_URL = "자기 서버주소";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if(null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }

}
