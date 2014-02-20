package com.m2mobi.guru;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.m2mobi.guru.utils.LoginUtils;

import static com.m2mobi.guru.utils.Constants.SHARED_PREFERENCES;

/**
 * Created by m2mobi on 2/7/14.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String LOG_TAG = "LoginActivity";

    private Button mLoginButton;
    private TextView mLoginUsername;
    private TextView mLoginPassword;

    private SharedPreferences mPreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mPreferences = getSharedPreferences(SHARED_PREFERENCES._PREFERENCES_FILENAME, Context.MODE_PRIVATE);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginUsername = (TextView) findViewById(R.id.login_username);
        mLoginPassword = (TextView) findViewById(R.id.login_password);

        mLoginButton.setOnClickListener(this);

        // Load saved username if present
        mLoginUsername.setText(mPreferences.getString(SHARED_PREFERENCES.USERNAME, ""));
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.login_button:

                String username = mLoginUsername.getText().toString();
                String password = mLoginPassword.getText().toString();

                if(!LoginUtils.isValidUsername(username)){
                    mLoginUsername.setError("Invalid username.");
                    return;
                } else {
                    mPreferences.edit().putString(SHARED_PREFERENCES.USERNAME, username).commit();
                }

                if (!LoginUtils.isValidPassword(password)) {
                    mLoginPassword.setError("Invalid password.");
                    return;
                }

                if(LoginUtils.isValidLogin(username, password)){
                    performLogin();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(this).create();
                    dialog.setTitle("Guru");
                    dialog.setMessage("Unknown usercombination; invalid username/password combination.");
                    dialog.setIcon(android.R.drawable.ic_dialog_alert);
                    dialog.show();
                }
                break;
            default:
                throw new IllegalArgumentException("Element has no click event defined.");
        }
    }


    private void performLogin(){
        Intent intent = new Intent(this, CountryListActivity.class);
        startActivity(intent);
    }
}