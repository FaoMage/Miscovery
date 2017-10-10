package com.dh.agus.digitalhousemusic.View;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.agus.digitalhousemusic.R;

public class LoginActivity extends AppCompatActivity {

    public static final Integer REQUEST_LOGIN = 125;
    public static final String KEY_MESSAGE = "KEY_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView textViewTopBar = findViewById(R.id.textViewTopBar);
        textViewTopBar.setText(R.string.login_login_to_miscovery);

        TextView textViewMessage = findViewById(R.id.textViewLoginMessage);
        textViewMessage.setText(bundle.getString(KEY_MESSAGE));

        // Se setea el ClickListener del boton <.
        ImageView imageViewBackButton = findViewById(R.id.imageViewBack);
        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Se setea el ClickListener del boton para registrarse
        TextView textViewCreateAccount = findViewById(R.id.textView_login_createAccount);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,
                        "Funcion: Registro de cuenta nueva", Toast.LENGTH_SHORT).show();
            }
        });

        // Se setea el ClickListener del boton para recuperar cuenta
        TextView textViewForgotPassword = findViewById(R.id.textView_login_forgotPassword);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,
                        "Funcion: Recuperar password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void login (View view) {
        Boolean inputEmailOk = false;
        Boolean inputPasswordOk = false;
        TextInputLayout textInputLayoutEmail = findViewById(R.id.textInputLayout_login_email);
        TextInputLayout textInputLayoutPassword = findViewById(R.id.textInputLayout_login_password);
        EditText editTextEmail = findViewById(R.id.editText_login_email);
        EditText editTextPassword = findViewById(R.id.editText_login_password);

        if (!isValidEmail(editTextEmail.getText().toString())) {
            textInputLayoutEmail.setError("Ingrese un email valido.");
        } else {
            textInputLayoutEmail.setError(null);
            inputEmailOk = true;
        }

        inputPasswordOk = true;

        if (inputEmailOk && inputPasswordOk) {
            Intent intent = new Intent(this,MainActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString(MainActivity.KEY_EMAIL,editTextEmail.getText().toString());
            bundle.putString(MainActivity.KEY_PASSWORD,editTextPassword.getText().toString());

            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
}
