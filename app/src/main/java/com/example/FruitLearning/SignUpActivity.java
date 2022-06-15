package com.example.FruitLearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private ImageView imgvlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        initUI();
        initListener();
    }

    private void initUI(){
        progressDialog = new ProgressDialog(this);
        imgvlogo = findViewById(R.id.imgv_kid);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnSignUp = findViewById(R.id.buttonSignUp);

        LoadImage();
    }

    private void LoadImage() {
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

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
    }

    private void onClickSignUp() {
        String strEmail = editTextEmail.getText().toString().trim();
        String strPass = editTextPassword.getText().toString().trim();
        String strConfirmPass = editTextConfirmPassword.getText().toString().trim();

        if (strEmail.isEmpty() || strPass.isEmpty() || strConfirmPass.isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please fill in all the information!",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (strPass.equals(strConfirmPass)) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                progressDialog.show();
                auth.createUserWithEmailAndPassword(strEmail, strPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            } else {
                Toast.makeText(SignUpActivity.this, "Confirm password failed",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void viewPassword(View view) {
        if (editTextPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void viewConfirmPassword(View view) {
        if (editTextConfirmPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            editTextConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
}