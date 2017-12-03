package com.dh.agus.digitalhousemusic.View.LoginActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;
import com.dh.agus.digitalhousemusic.View.MainActivity.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppActivity {

    public static final Integer REQUEST_LOGIN = 125;
    public static final String KEY_MESSAGE = "KEY_MESSAGE";

    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.dialog_signup_loginin));
        progressDialog.setCancelable(false);

        setAppBarContext(LoginActivity.this, this);
        implementAppBar();
        setTitle(R.string.appbar_login_to_miscovery);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            TextView textViewMessage = findViewById(R.id.textViewLoginMessage);
            textViewMessage.setText(bundle.getString(KEY_MESSAGE));
        }

        // Se setea el ClickListener del boton para registrarse
        TextView textViewCreateAccount = findViewById(R.id.textView_login_createAccount);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intentRegister,RegisterActivity.REQUEST_REGISTER);
            }
        });

        // Se setea el ClickListener del boton para recuperar cuenta
        TextView textViewForgotPassword = findViewById(R.id.textView_login_forgotPassword);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRecover = new Intent(LoginActivity.this,RecoverPasswordActivity.class);
                startActivity(intentRecover);
            }
        });

        //Facebook
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button_facebook);
        loginButton.setReadPermissions("email", "public_profile");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog.show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this,
                        R.string.login_error_facebook,
                        Toast.LENGTH_SHORT).show();
                Log.d("------",exception.toString());
            }
        });
    }

    // Firebase with Facebook
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            AlertDialog dialog = builder.setMessage(R.string.login_facebook_colition)
                                    .setTitle(R.string.login_emailalreadyregistered)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .create();
                            dialog.show();
                            LoginManager.getInstance().logOut();
                        }
                    }
                });
    }

    // login firebase
    public void login (View view) {
        final TextInputLayout textInputLayoutEmail = findViewById(R.id.textInputLayout_login_email);
        final TextInputLayout textInputLayoutPassword = findViewById(R.id.textInputLayout_login_password);

        EditText editTextEmail = findViewById(R.id.editText_login_email);
        EditText editTextPassword = findViewById(R.id.editText_login_password);

        String user = editTextEmail.getText().toString();
        String pw = editTextPassword.getText().toString();

        textInputLayoutEmail.setError(null);
        textInputLayoutPassword.setError(null);

        if (user.length() > 0 && pw.length() > 0) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Ingresando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(user, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        Log.d("-------",task.getException().toString());
                        progressDialog.dismiss();
                        if (task.getException().getClass() == FirebaseAuthInvalidCredentialsException.class) {
                            textInputLayoutPassword.setError(getString(R.string.login_wrong_password));
                        } else if (task.getException().getClass() == FirebaseAuthInvalidUserException.class){
                            textInputLayoutEmail.setError(getString(R.string.login_emailnotregistered));
                        } else if (task.getException().getClass() == FirebaseNetworkException.class) {
                            connectionErrorDialogShow(LoginActivity.this);
                        }
                    }
                }
            });
        } else {
            if (user.length() == 0) textInputLayoutEmail.setError(getString(R.string.login_incomplete_field));
            if (pw.length() == 0) textInputLayoutPassword.setError(getString(R.string.login_incomplete_field));
        }
    }

    protected static void connectionErrorDialogShow (Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.setMessage(R.string.login_nointernet_message)
                .setTitle(R.string.login_nointernet_title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegisterActivity.REQUEST_REGISTER ) {
            if (requestCode == RESULT_OK) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
