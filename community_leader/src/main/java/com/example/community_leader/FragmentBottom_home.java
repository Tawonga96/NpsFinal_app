package com.example.community_leader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBottom_home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBottom_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    public FragmentBottom_home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBottom_home.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBottom_home newInstance(String param1, String param2) {
        FragmentBottom_home fragment = new FragmentBottom_home();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //CREATE A RECYCLER VIEW
        List<recycler_items> items= new ArrayList<recycler_items>();
        items.add(new recycler_items("john wick","john@gmail.com",R.drawable.imag1));
        items.add(new recycler_items("Blood spill","blood@gmail.com",R.drawable.img4));
        items.add(new recycler_items("Dirty","john@gmail.com",R.drawable.imag1));
        items.add(new recycler_items("Bossy","Bossy@gmail.com",R.drawable.img3));
        items.add(new recycler_items("john wick","john@gmail.com",R.drawable.imag1));
        items.add(new recycler_items("Blood spill","blood@gmail.com",R.drawable.img4));
        items.add(new recycler_items("Dirty","john@gmail.com",R.drawable.imag1));
        items.add(new recycler_items("Bossy","Bossy@gmail.com",R.drawable.img3));
        items.add(new recycler_items("Blood spill","blood@gmail.com",R.drawable.img4));
        items.add(new recycler_items("Dirty","john@gmail.com",R.drawable.imag1));
        items.add(new recycler_items("Bossy","Bossy@gmail.com",R.drawable.img3));
        items.add(new recycler_items("john wick","john@gmail.com",R.drawable.imag1));
        items.add(new recycler_items("Blood spill","blood@gmail.com",R.drawable.img4));

        RecyclerView recyclerView =view.findViewById(R.id.recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter(getContext(),items));
    }
}