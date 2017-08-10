package com.ajaymehta.updateusername.activites;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ajaymehta.updateusername.R;
import com.ajaymehta.updateusername.helper.Preferencehelper;
import com.ajaymehta.updateusername.interfaces.RestApi;
import com.ajaymehta.updateusername.model.username.Username;
import com.ajaymehta.updateusername.util.UtilsManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// why we need userId ...see when user register in any app...it creates and give that unique id to  every particular user...so that if that
// user wanna do something ...than the app creator can access all his data and information using his id....and perform task..
//like here in this case user just has to tell his id..n app will upadate...his username..boom!

public class MainActivity extends AppCompatActivity {

    // dont forget to add the internet permission

    EditText mID , mUsername;
    Preferencehelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mID = (EditText) findViewById(R.id.et_id);
        mUsername = (EditText) findViewById(R.id.et_username);

        // this reason we we using sharedPreference...see when user register we get his userId and put in shared prefence...so that we just save it once..and wherever we want..we can use it in project..in any class....

        helper = new Preferencehelper(this);  // this is our custom class ..in that we performing functionality of shared preference..

       ;
    }



    private void usernamechange() {

        int userID = Integer.parseInt(mID.getText().toString());
        String userUsername = mUsername.getText().toString();

        helper.setUid(userID);


        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading.....");
        progress.setCancelable(false);
        progress.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.sisgain.com/project/sawa/")
                .addConverterFactory(GsonConverterFactory.create())   // add dependices converter gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())     // add dependicnce adapter rx java..
                .build();

        RestApi api = retrofit.create(RestApi.class);

        //  String token = helper.getTOKEN();


        Call<Username> loginResponseCall;

        loginResponseCall = api.getUsername(userUsername, helper.getUid() );   // 7 is available id  // helper.getUserId()  when user sign up you can store id in helper class and use it anywhere in the project..now we putting manully in this example..

        loginResponseCall.enqueue(new Callback<Username>() {   // for this to work add dependency --> okhttp
                                      @Override
                                      public void onResponse(Call<Username> call, Response<Username> response) {

                                          if (response.body().getCode() == 200) {

                                              Toast.makeText(MainActivity.this, "your new username is: "+ response.body().getResult().getU_name(),Toast.LENGTH_LONG).show();

                                              progress.dismiss();
                                              UtilsManager.showAlertBox(response.body().getResult().getMsg(), "Android Project",MainActivity.this);

                                          } else if (response.body().getCode() == 402) {
                                              progress.dismiss();


                                              UtilsManager.showAlertBox(response.body().getResult().getMsg(), "Android Project",MainActivity.this);

                                          } else if (response.body().getCode() == 401) {

                                              progress.dismiss();

                                             UtilsManager.showAlertBox(response.body().getResult().getMsg(), "Android Project",MainActivity.this);
                                          } else {
                                              progress.dismiss();
                                                UtilsManager.showAlertBox(response.body().getResult().getMsg(), "Android Project", MainActivity.this);
                                          }


                                      }


                                      @Override
                                      public void onFailure(Call<Username> call, Throwable t) {
                                          progress.dismiss();
                                          Log.d("TAG", "AjayMehta" + t.getMessage());
                                      }
                                  }

        );


    }

    public void submit(View view) {

        usernamechange();
    }
}



/*

------------Update User Name
        =================
        url:-http://sisgain.com/project/middleeast/UserPro/userName/
        ======================
        parameter
        userid:52
        username:raj12
        ======================
        {"code":200,"result":{"msg":"Name Update Successfully.."}}
        ======================

        {"code":401,"result":{"msg":"Invalid parameter or value passed"}}
        =====================
        {"code":402,"result":{"msg":"invalid userid"}}
        =====================


        this is the parament and url...first check if api is working..using --> postman...
        then ..the json created by postman ..get its field by using jsonschema2pojo website....
        then use it....
*/
