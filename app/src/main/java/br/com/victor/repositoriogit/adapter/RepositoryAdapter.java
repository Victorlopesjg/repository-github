package br.com.victor.repositoriogit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.victor.repositoriogit.R;
import br.com.victor.repositoriogit.model.Repository;

/**
 * Created by Victor Oliveira on 20/08/18.
 */
public class RepositoryAdapter extends BaseAdapter {
    private Context context;
    private List<Repository> repositoryList;

    public RepositoryAdapter(Context context, List<Repository> repositoryList) {
        this.context = context;
        this.repositoryList = repositoryList;
    }

    @Override
    public int getCount() {
        return (repositoryList != null && !repositoryList.isEmpty()) ? repositoryList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return (repositoryList != null && !repositoryList.isEmpty()) ? repositoryList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.item_repository, null);

        if (repositoryList != null && !repositoryList.isEmpty()) {
            Repository repository = repositoryList.get(i);
            TextView txtRepository = view.findViewById(R.id.txtRepo);
            TextView txtLanguage = view.findViewById(R.id.txtLanguage);

            txtRepository.setText(repository.getName());
            txtLanguage.setText(repository.getLanguage());

            return view;
        }

        return null;
    }
}
