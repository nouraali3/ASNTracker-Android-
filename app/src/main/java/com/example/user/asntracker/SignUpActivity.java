package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends AppCompatActivity {

    EditText nameET, emailET, phoneNumberET, genderET, passwordET, rePasswordET;
    String name, email, phoneNumber, gender, password, rePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setViews();

    }


    //TODO: take gender
    private void setViews()
    {
        nameET = findViewById(R.id.name_et);
        emailET = findViewById(R.id.email_et);
        phoneNumberET = findViewById(R.id.phone_et);
        genderET = findViewById(R.id.gender_et);
        passwordET = findViewById(R.id.password_et2);
        rePasswordET = findViewById(R.id.re_password_et);
    }

    public void signup(View v)
    {
        setUserInput();
        if(twoPasswordsAreEqual() && allFieldsAreFilled())
            {handleSignup();}
        else
        {
            TextView passwordError = findViewById(R.id.password_error_tv);
            passwordError.setText("passwords don't match");
        }
    }

    public void choosePhotoFromGallery(View v)
    {

    }
    private void setUserInput()
    {
        name=nameET.getText().toString();
        email=emailET.getText().toString();
        phoneNumber=phoneNumberET.getText().toString();
        gender = genderET.getText().toString();
        password=passwordET.getText().toString();
        rePassword=rePasswordET.getText().toString();

    }

    private boolean twoPasswordsAreEqual()
    {return password.equals(rePassword);}

    private boolean allFieldsAreFilled()
    {
        return ( name.length()>0 && email.length()>0 && phoneNumberET.length()>0 && password.length()>0 && rePassword.length()>0 );
    }

    private void handleSignup()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url ="http://asnasucse18.000webhostapp.com/RFTDA/Signup.php";
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("email",email);
        params.put("phoneNumber",phoneNumber);
        params.put("password",password);
        params.put("gender",gender);
        client.get(url,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(response.getBoolean("result")==true)
                    {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });

    }
}
