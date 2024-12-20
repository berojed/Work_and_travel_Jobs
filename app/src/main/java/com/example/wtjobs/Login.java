package com.example.wtjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wtjobs.kotlinUI.screens.EmployerPageKt;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText email,password;
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView registernow;

    @Override
    public void onStart() {
        super.onStart();
        // Dohvaćanje trenutnog korisnika pomoću metode getCurrentUser() i provjera da li korisnik postoji, ako postoji, preusmerit će se na main activity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicijaliziranje instance na FirebaseAuth i
        mAuth= FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        progressBar=findViewById(R.id.progressBar);
        registernow=findViewById(R.id.registerNow);


        //Postavljanje listenera na registernow gumb koji preusmerava korisnika na registraciju
        registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                finish();
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String Email,Password;
                Email=String.valueOf(email.getText());
                Password=String.valueOf(password.getText());

                if(Email.isEmpty() || Password.isEmpty())
                {
                    Toast.makeText(Login.this, "Niste unijeli sve ispravne podatke", Toast.LENGTH_SHORT).show();
                }

                // Pokušava prijaviti korisnika koristeći email i lozinku pomoću metode signInWithEmailAndPassword()

                mAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            //Metoda onComplete() se poziva kada se prijava završi
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // Provjera uspješnosti prijave
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(),"Uspješna prijava", Toast.LENGTH_SHORT).show();
                                    if(Email.equals("admin@admin.com"))
                                    {
                                        Intent intent=new Intent(getApplicationContext(), EmployerActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                } else {


                                    Toast.makeText(Login.this, "Neuspješna prijava.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


    }
}