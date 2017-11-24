package com.dh.agus.digitalhousemusic.View.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;
import com.dh.agus.digitalhousemusic.View.MainActivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        setTitle("Register to Miscovery");

        mAuth = FirebaseAuth.getInstance();
    }

    private static boolean isValidEmail(String email) {
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
            textInputLayoutEmail.setError("Ingrese un email valido");
        } else {
            textInputLayoutEmail.setError(null);
            inputEmailOk = true;
        }

        // valida pws
        if (editTextPassword.getText().length() < 8) {
            textInputLayoutPassword.setError("La contraseña debe tener mas de 8 caracteres");
        } else {
            textInputLayoutPassword.setError(null);

            if (!editTextPassword.getText().toString().equals(editTextPasswordR.getText().toString())) {
                textInputLayoutPasswordR.setError("Las contraseñas deben ser iguales");
            } else {
                textInputLayoutPasswordR.setError(null);
                inputPasswordOk = true;
            }
        }

        // devuelve los datos
        if (inputEmailOk && inputPasswordOk) {
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),
                    editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this,
                                        LoginActivity.class);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            } else {
                                if (task.getException().getClass() == FirebaseAuthUserCollisionException.class) {
                                    textInputLayoutEmail.setError("El mail se encuentra en uso");
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            "Ocurrió un error :( Intenta mas tarde",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }
}
