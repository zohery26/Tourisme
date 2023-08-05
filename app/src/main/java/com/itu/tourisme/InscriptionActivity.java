package com.itu.tourisme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InscriptionActivity extends AppCompatActivity {

    private static final String NODEJS_WEBSERVICE_URL = "http://madatourismeitu.alwaysdata.net/inscription";
    EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        TextView lienConnexion = (TextView) findViewById(R.id.lienConnexion);
        Button buttonInscription = (Button) findViewById(R.id.btnInscription);

        editTextUsername = findViewById(R.id.inscrinom);
        editTextEmail = findViewById(R.id.inscrimail);
        editTextPassword = findViewById(R.id.inscriMdp);
        editTextConfirmPassword = findViewById(R.id.inscriConfirmMdp);

        buttonInscription.setOnClickListener(v -> inscription());

        lienConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToastSuccess() {
        Toast.makeText(this, "Inscription reussi!", Toast.LENGTH_SHORT).show();
    }

    private void showToastFail() {
        Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
    }

    private void inscription() {
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // Vérifier mdp
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Les mots de passe ne correspondent pas.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérifier adresse
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Adresse e-mail invalide.", Toast.LENGTH_SHORT).show();
            return;
        }

        // appele du WebService
        inscriptionWebService(username, email, password);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void inscriptionWebService(String username, String email, String password) {
        new InscriptionTask().execute(username, email, password);
    }

    private class InscriptionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String email = params[1];
            String password = params[2];

            try {
                URL url = new URL(NODEJS_WEBSERVICE_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String data = "nomPrenoms=" + URLEncoder.encode(username, "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8") +
                        "&mdp=" + URLEncoder.encode(password, "UTF-8");

                DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                outputStream.writeBytes(data);
                outputStream.flush();
                outputStream.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                showToastSuccess();
            } else {
                showToastFail();
            }
        }
    }
}