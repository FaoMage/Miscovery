package com.dh.agus.digitalhousemusic.View.LoginActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppActivity {

    public static final Integer REQUEST_REGISTER = 1250;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setAppBarContext(RegisterActivity.this, this);
        implementAppBar();
        setTitle(getString(R.string.appbar_register));

        mAuth = FirebaseAuth.getInstance();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void register (View view) {
        Boolean inputEmailOk = false;
        Boolean inputPasswordOk = false;

        final TextInputLayout textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayout_signUp_email);
        TextInputLayout textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayout_signUp_password);
        TextInputLayout textInputLayoutPasswordR = (TextInputLayout) findViewById(R.id.textInputLayout_signUp_password_r);

        EditText editTextEmail = (EditText) findViewById(R.id.editText_signUp_email);
        EditText editTextPassword = (EditText) findViewById(R.id.editText_signUp_password);
        EditText editTextPasswordR = (EditText) findViewById(R.id.editText_signUp_password_r);

        // valida email
        if (!isValidEmail(editTextEmail.getText().toString())) {
            textInputLayoutEmail.setError(getString(R.string.register_invalid_email));
        } else {
            textInputLayoutEmail.setError(null);
            inputEmailOk = true;
        }

        // valida pws
        if (editTextPassword.getText().length() < 8) {
            textInputLayoutPassword.setError(getString(R.string.register_password_format));
        } else {
            textInputLayoutPassword.setError(null);

            if (!editTextPassword.getText().toString().equals(editTextPasswordR.getText().toString())) {
                textInputLayoutPasswordR.setError(getString(R.string.register_passwords_unequal));
            } else {
                textInputLayoutPasswordR.setError(null);
                inputPasswordOk = true;
            }
        }

        // devuelve los datos
        if (inputEmailOk && inputPasswordOk) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.dialog_registering_account));
            progressDialog.setCancelable(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),
                    editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this,
                                        LoginActivity.class);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            } else {
                                if (task.getException().getClass() == FirebaseAuthUserCollisionException.class) {
                                    textInputLayoutEmail.setError(getString(R.string.register_email_in_use));
                                } else if (task.getException().getClass() == FirebaseNetworkException.class) {
                                    LoginActivity.connectionErrorDialogShow(RegisterActivity.this);
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            R.string.login_error,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }
}
