package com.example.FruitLearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText editTextEmail, editTextPassword;
    private ProgressDialog progressDialog;
    private ImageView imgvlogo;

    private LinearLayout layoutSignUp;

    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_login));
        initUI();
        initListener();
    }
    private void initUI(){
        progressDialog = new ProgressDialog(this);

        imgvlogo = findViewById(R.id.imgv_kid);
        layoutSignUp = findViewById(R.id.layoutSignUp);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        LoadImage();
    }

    private void LoadImage()
    {
        try {
            // get input stream

            InputStream ims = getAssets().open("images/kid_1.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imgvlogo.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListener(){
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
    }

    private void onClickLogin() {
        String strEmail = editTextEmail.getText().toString().trim();
        String strPass = editTextPassword.getText().toString().trim();
        if (strEmail == null || strPass == null){
            Toast.makeText(LoginActivity.this, "Please fill in all the information!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.signInWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
