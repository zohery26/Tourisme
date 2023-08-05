package com.itu.tourisme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListetourismeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListetourismeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public ListetourismeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListetourismeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListetourismeFragment newInstance(String param1, String param2) {
        ListetourismeFragment fragment = new ListetourismeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private List<ListeDestination> destinationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListeAdapter listeAdapter;
    private EditText searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listetourisme, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listeAdapter = new ListeAdapter(destinationList);
        recyclerView.setAdapter(listeAdapter);

        //Get destinations
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<List<ListeDestination>> call = apiService.getItems();
        call.enqueue(new Callback<List<ListeDestination>>() {
            @Override
            public void onResponse(Call<List<ListeDestination>> call, Response<List<ListeDestination>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    destinationList.addAll(response.body());
                    listeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ListeDestination>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur lors de la récupération des données", Toast.LENGTH_SHORT).show();
            }
        });

        searchEditText = view.findViewById(R.id.edittext);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        return view;
    }

    public void filter(String text) {
        ArrayList<ListeDestination> listeDestinationsFiltre = new ArrayList<>();

        for (ListeDestination listeDestination : destinationList) {
            String description = listeDestination.getDescription();
            String title = listeDestination.getTitle();

            if ((description != null && description.toLowerCase().contains(text.toLowerCase()))
                    || (title != null && title.toLowerCase().contains(text.toLowerCase()))) {
                listeDestinationsFiltre.add(listeDestination);
            }
        }

        listeAdapter.filterList(listeDestinationsFiltre);
    }

}