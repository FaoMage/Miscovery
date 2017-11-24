package com.dh.agus.digitalhousemusic.View.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;
import com.dh.agus.digitalhousemusic.View.MainActivity.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppActivity {

    public static final Integer REQUEST_LOGIN = 125;
    public static final String KEY_MESSAGE = "KEY_MESSAGE";

    public static final String KEY_USER = "KEY_USER";

    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        setAppBarContext(LoginActivity.this, this);
        implementAppBar();
        setTitle(R.string.login_login_to_miscovery);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            TextView textViewMessage = (TextView) findViewById(R.id.textViewLoginMessage);
            textViewMessage.setText(bundle.getString(KEY_MESSAGE));
        }

        // Se setea el ClickListener del boton para registrarse
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textView_login_createAccount);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intentRegister,RegisterActivity.REQUEST_REGISTER);
            }
        });

        // Se setea el ClickListener del boton para recuperar cuenta
        TextView textViewForgotPassword = (TextView) findViewById(R.id.textView_login_forgotPassword);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,
                        "Funcion: Recuperar password", Toast.LENGTH_SHORT).show();
            }
        });


        //Facebook
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button_facebook);
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();

                Profile profile = Profile.getCurrentProfile();
                String user = profile.getName();

                bundle.putString(LoginActivity.KEY_USER,user);

                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, "Se produjo un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // esto se va y se hace con firebase
    public void login (View view) {
        Intent intent = new Intent(this,MainActivity.class);
        setResult(Activity.RESULT_OK,intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegisterActivity.REQUEST_REGISTER) {

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
