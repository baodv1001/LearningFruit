package com.example.FruitLearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button btnSignUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        initUI();
        initListener();
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

        if (strEmail == null || strPass == null || strConfirmPass == null){
            Toast.makeText(SignUpActivity.this, "Please fill in all the information!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (strPass == strConfirmPass) {
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
                                finish();
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

    private void initUI(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnSignUp = findViewById(R.id.buttonSignUp);
        progressDialog = new ProgressDialog(this);
    }
}