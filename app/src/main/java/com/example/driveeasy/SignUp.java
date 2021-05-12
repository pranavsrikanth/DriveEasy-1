package com.example.driveeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private static final String TAG="EmailPassword";
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
    }
    public void onStart() {
       Button next=findViewById(R.id.next);
       EditText name=findViewById(R.id.name);
       EditText user=findViewById(R.id.user);
       EditText pass=findViewById(R.id.pass);
       EditText confirmpass=findViewById(R.id.confirmpass);

        super.onStart();
        next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootNode= FirebaseDatabase.getInstance();
                    reference=rootNode.getReference("name");
                    UserHelperClass HelperClass = new UserHelperClass(name.getText().toString(),user.getText().toString(),pass.getText().toString(),confirmpass.getText().toString());
                    createAccount(name.getText().toString(),user.getText().toString(),pass.getText().toString());

                }
            });
    }

    private void createAccount(String name,String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }
    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(SignUp.this, Dashboard.class);
        startActivity(intent);
        finish();

    }
}