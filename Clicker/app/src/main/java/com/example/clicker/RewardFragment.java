package com.example.clicker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RewardFragment extends Fragment
{
    private World world;

    public RewardFragment()
    {
        //need empty constructor
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_reward, container, false);

        TextView reward = (TextView) view.findViewById(R.id.rewardMiddleText);
        reward.setText(String.valueOf(world.getPaid()));
        return view;
    }
}
