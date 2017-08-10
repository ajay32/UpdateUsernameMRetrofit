package com.ajaymehta.updateusername.interfaces;


import com.ajaymehta.updateusername.model.username.Username;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RestApi {


    @FormUrlEncoded
    @POST("UserPro/userName/")
    Call<Username> getUsername(

            @Field("username") String username,
            @Field("userid")    int userid);




}
