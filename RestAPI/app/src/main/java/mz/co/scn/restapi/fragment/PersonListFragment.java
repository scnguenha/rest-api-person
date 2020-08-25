package mz.co.scn.restapi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import mz.co.scn.restapi.R;
import mz.co.scn.restapi.adapter.PersonListAdapter;
import mz.co.scn.restapi.model.Person;
import mz.co.scn.restapi.sync.ReceivePersonAsync;

public class PersonListFragment extends Fragment {

    private PersonListAdapter personListAdapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    protected List<Person> persons;

    public PersonListFragment(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_person, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);

        swipeRefreshLayout = v.findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            new ReceivePersonAsync(getContext()).execute();
            swipeRefreshLayout.setRefreshing(false);
        });

        //Create a list of persons
        //new PersonSync(getContext()).execute();

        personListAdapter = new PersonListAdapter(persons);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(personListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        personListAdapter.notifyDataSetChanged();
        return v;
    }
}