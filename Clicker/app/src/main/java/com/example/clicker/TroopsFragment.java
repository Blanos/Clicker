package com.example.clicker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TroopsFragment extends Fragment
{
    private World world;

    public void setWorld(World world)
    {
        this.world = world;
    }

    public TroopsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_troops, container, false);
        ListView troopsList = (ListView) view.findViewById(R.id.Troops_List);
        ListAdapter troopAdapter = new TroopsCustomAdapter(getActivity(), world.getTroops(), world);
        troopsList.setAdapter(troopAdapter);

        return view;
    }
}
