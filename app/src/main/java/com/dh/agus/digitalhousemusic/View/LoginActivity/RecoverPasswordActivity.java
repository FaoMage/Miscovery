package com.dh.agus.digitalhousemusic.View.LoginActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dh.agus.digitalhousemusic.R;
import com.dh.agus.digitalhousemusic.View.AppActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class RecoverPasswordActivity extends AppActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        mAuth = FirebaseAuth.getInstance();

        setAppBarContext(RecoverPasswordActivity.this, this);
        implementAppBar();
        setTitle(getString(R.string.appbar_recover));
    }

    public void recover(View view) {
        final TextInputLayout textInputLayoutEmail = findViewById(R.id.textInputLayout_recover_email);
        EditText editTextEmail = findViewById(R.id.editText_recover_email);
        String email = editTextEmail.getText().toString();

        textInputLayoutEmail.setError(null);

        if (RegisterActivity.isValidEmail(email) && email.length() > 0) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.dialog_processing_order));
            progressDialog.setCancelable(false);
            progressDialog.show();
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    Log.d("--------",task.getException().getMessage());
                    if (task.isSuccessful()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPasswordActivity.this);
                        AlertDialog dialog = builder.setMessage(R.string.login_facebook_colition)
                                .setTitle(R.string.recover_invalid_mail)
                                .setMessage(R.string.recover_search_emails)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .create();
                        dialog.show();
                    } else if (task.getException().getClass() == FirebaseAuthInvalidUserException.class) {
                        textInputLayoutEmail.setError(getString(R.string.login_emailnotregistered));
                    } else if (task.getException().getMessage().equals("An internal error has occurred. [ <<Network Error>> ]")) {
                        LoginActivity.connectionErrorDialogShow(RecoverPasswordActivity.this);
                    }
                }
            });
        } else {
            textInputLayoutEmail.setError(getString(R.string.register_invalid_email));
        }
    }
}
