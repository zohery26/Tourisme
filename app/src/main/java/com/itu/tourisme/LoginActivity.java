package com.itu.tourisme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView lienInscription = (TextView) findViewById(R.id.lienConnexion);
        lienInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });

        loginButton = (Button) findViewById(R.id.btn_login);

        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/

        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                new LoginTask().execute(email, password);
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String loginUrl = "https://madatourismeitu.alwaysdata.net/connexion";

            String email = params[0];
            String password = params[1];

            try {
                URL url = new URL(loginUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                String postData = "email=" + email + "&mdp=" + password;
                byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postDataBytes);
                outputStream.flush();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();

                return response.toString();

            } catch (IOException e) {
                Log.e(TAG, "Error in login request: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                if(result.equalsIgnoreCase("mot_de_passe_incorrect")) {
                    Toast.makeText(LoginActivity.this, "Mot de passe incorrect!", Toast.LENGTH_SHORT).show();
                } else if(result.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Une erreur est survenue lors de la connexion.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(LoginActivity.this, "Une erreur est survenue lors de la connexion.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}