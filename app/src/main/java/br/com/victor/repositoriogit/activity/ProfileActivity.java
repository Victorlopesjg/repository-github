package br.com.victor.repositoriogit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import br.com.victor.repositoriogit.R;
import br.com.victor.repositoriogit.adapter.RepositoryAdapter;
import br.com.victor.repositoriogit.model.Owner;
import br.com.victor.repositoriogit.model.Repository;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private final static String KEY_REPOSITORY = "KEY_REPOSITORY";
    private List<Repository> repositoryList;
    private CircleImageView imgProfile;
    private TextView txtUserName;
    private ListView listRepo;
    private RepositoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgProfile = findViewById(R.id.imgProfile);
        txtUserName = findViewById(R.id.txtUserName);
        listRepo = findViewById(R.id.listRepo);

        if (savedInstanceState != null) {
            repositoryList = (List<Repository>) savedInstanceState.get(KEY_REPOSITORY);
        } else {
            repositoryList = (List<Repository>) getIntent().getExtras().get(getString(R.string.key_intent_repository));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Owner owner = repositoryList.get(0).getOwner();

        // Setando o user name do usuário
        txtUserName.setText(owner.getLogin());

        // Baixando o avatar do usuário
        Picasso.get().load(owner.getAvatarUrl()).into(imgProfile);

        adapter = new RepositoryAdapter(getApplicationContext(), repositoryList);
        listRepo.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_REPOSITORY, (Serializable) repositoryList);
    }
}
