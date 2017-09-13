package com.example.hosiluan.foodanddrink.loginactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.mainactivity.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGIN_TOKEN = "login token";
    public static final String ACCESS_TOKEN = "access token";
    public static final String USER_ID = "user id";
    public static final String FB_USER_NAME = "user name";
    public static final String USER_EMAIL = "user email";
    public static final String USER_GENDER = "user gender";
    public static final String USER_PROFILE_PIC = "user profile pic";


    LoginButton loginButton;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    private ImageView imgLoginIcon;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("Luan","the helllll");
        sharedPreferences = getSharedPreferences(LOGIN_TOKEN, Context.MODE_PRIVATE);

        if (checkLoginStatus()) {
            Log.d("Luan", "khac null");

            String name = sharedPreferences.getString(FB_USER_NAME, null);
            String id = sharedPreferences.getString(USER_ID, null);

            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("id", id);

            startMainActivity(bundle);

        } else {
            Log.d("Luan", "== null");
            TypedArray images = getResources().obtainTypedArray(R.array.img_login);

            imgLoginIcon = (ImageView) findViewById(R.id.img_login_icon);
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderWidthDp(1)
                    .cornerRadiusDp(100)
                    .oval(false)
                    .build();
            Picasso.with(this).load(images.getResourceId(0,-1))
                    .transform(transformation).into(imgLoginIcon);
            requestLogin();
        }
    }

    public void startMainActivity(Bundle bundle) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("loginResult", bundle);
        startActivity(intent);
    }

    public boolean checkLoginStatus() {
        String token = sharedPreferences.getString(ACCESS_TOKEN, null);
        if (token != null) {
            return true;
        }
        return false;
    }

    private void requestLogin() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Luan", "success");

                String accessToken = loginResult.getAccessToken().getToken();

                sharedPreferences = getSharedPreferences(LOGIN_TOKEN, Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(ACCESS_TOKEN, accessToken);

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String name = object.getString("name");
                            String id = object.getString("id");
//                            String email = object.getString("email");
//                            String gender = object.getString("gender");
                            String email = "";
                            String gender = "";

                            if (object.has("email"))
                            {
                                email = object.getString("email");
                            }
                            if (object.has("gender"))
                            {
                                gender = object.getString("gender");
                            }

                            Log.d("Luan",email + " / " + gender);

                            Bundle bundle = new Bundle();
                            bundle.putString("name", name);
                            bundle.putString("id", id);

                            editor.putString(FB_USER_NAME, name);
                            editor.putString(USER_ID, id);
                            editor.putString(USER_EMAIL,email);
                            editor.putString(USER_GENDER,gender);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("loginResult", bundle);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("Luan", "fail");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Luan", error.toString());

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
