package batchtwo.mostain.lict.com.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignup  = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);

        //attaching click listener
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignin();
            }
        });
        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }
    //method for user login
    private void userSignin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        }
                    }
                });
    }

}
