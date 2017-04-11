package com.example.clicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TroopsCustomAdapter extends ArrayAdapter<Troop>
{
    private List<Troop> troops;
    private World world;

    public TroopsCustomAdapter(Context context, List<Troop> troops, World world)
    {
        super(context, R.layout.troop_list_row, troops);
        this.troops = troops;
        this.world = world;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = convertView;
        if (v == null)
        {
            v = inflater.inflate(R.layout.troop_list_row, parent, false);
        }

        final TextView troopLevel = (TextView) v.findViewById(R.id.TroopLevelN);
        final TextView troopDamage = (TextView) v.findViewById(R.id.TroopDamageN);
        final TextView troopCost = (TextView) v.findViewById(R.id.TroopLevelUpCostN);
        Button troopLvlUp = (Button) v.findViewById(R.id.TrainTroopButton);

        troopLvlUp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                if(world.getHero().getGold() >= troops.get(position).getCost())
                {
                    world.getHero().setGold(world.getHero().getGold() - troops.get(position).getCost());
                    troops.get(position).setCost((int) Math.pow(10, troops.get(position).getLevel() + 1));
                    troops.get(position).setLevel(troops.get(position).getLevel() + 1);
                    troops.get(position).setDamage(troops.get(position).getLevel());

                    troopLevel.setText(String.valueOf(troops.get(position).getLevel()));
                    troopDamage.setText(String.valueOf(troops.get(position).getDamage()));
                    troopCost.setText(String.valueOf(troops.get(position).getCost()));
                }
                else
                {
                    Toast.makeText(getContext(), "Not enough gold", Toast.LENGTH_LONG).show();
                }
            }
        });

        troopLevel.setText(String.valueOf(troops.get(position).getLevel()));
        troopDamage.setText(String.valueOf(troops.get(position).getDamage()));
        troopCost.setText(String.valueOf(troops.get(position).getCost()));

        return v;
    }
}
