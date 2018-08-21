package br.com.victor.repositoriogit.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.victor.repositoriogit.R;
import br.com.victor.repositoriogit.model.Repository;
import br.com.victor.repositoriogit.service.ServiceApp;

public class MainActivity extends AppCompatActivity {

    private final static String KEY_INPUT = "KEY_INPUT";

    private EditText inputUser;
    private Button btnSearch;
    private ProgressBar progress;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUser = findViewById(R.id.inputUser);
        btnSearch = findViewById(R.id.btnSearch);
        progress = findViewById(R.id.progress);

        if (savedInstanceState != null) {
            inputUser.setText(savedInstanceState.getString(KEY_INPUT));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = inputUser.getText().toString();

                if (!user.isEmpty()) {
                    new TaskGithub().execute(user);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a user.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        user = inputUser.getText().toString();

        if (!user.isEmpty()) {
            outState.putString(KEY_INPUT, user);
        }
    }

    public void createAletDialog(String messae) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messae)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class TaskGithub extends AsyncTask<String, Void, List<Repository>> {

        private String erro = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setParameters(true);
        }

        @Override
        protected List<Repository> doInBackground(String... users) {
            try {
                return ServiceApp.getInstance().getListRepository(users[0]);
            } catch (Exception e) {
                erro = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Repository> repositories) {
            super.onPostExecute(repositories);
            setParameters(false);

            if (repositories != null && !repositories.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(getString(R.string.key_intent_repository), (Serializable) repositories);

                startActivity(intent);
            } else {
                if (erro != null && !erro.isEmpty()) {
                    createAletDialog(erro);
                } else {
                    createAletDialog("No repository found for this user.");
                }
            }
        }

        private void setParameters(boolean status) {
            if (status) {
                progress.setVisibility(View.VISIBLE);
                inputUser.setVisibility(View.GONE);
                btnSearch.setVisibility(View.GONE);
            } else {
                progress.setVisibility(View.GONE);
                inputUser.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.VISIBLE);
            }
        }
    }
}
