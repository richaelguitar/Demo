package com.app.demo;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.demo.basic.BaseActivity;
import com.app.demo.schedule.SchedulerUtils;
import com.app.demo.util.LoginUtils;


/**
 * 登录界面
 */

public class LoginActivity extends BaseActivity {


    // UI references.

    private EditText mEmailView;



    private View mLoginFormView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginFormView = findViewById(R.id.email_login_form);
        mEmailView = findViewById(R.id.email);
        mEmailView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin(textView);
                    return true;
                }
                return false;
            }
        });
        if(LoginUtils.isLogin(this)){
            SchedulerUtils.with(this).scheduler();//开启刷新任务
            goHome();
        }
    }


    private String email,password;
    private boolean validateFrom(){
        // Reset errors.
        mEmailView.setError(null);


        // Store values at the time of the login attempt.
         email = mEmailView.getText().toString();


        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("账户必填");
            focusView = mEmailView;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
            return false;
        }
        return  true;
    }



    /**
     * 登录
     */
    public void attemptLogin(View view) {
        if(validateFrom()){
            //保存用户信息
            SharedPreferences preferences = LoginUtils.getLoginInfo(LoginActivity.this);
            preferences.edit()
                    .putBoolean("isLogin",true)
                    .putString("userId", email)
                    .commit();
            SchedulerUtils.with(this).scheduler();//开启刷新任务
            //登录成功 跳转到主页面
            goHome();
        }
    }

    private void goHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

