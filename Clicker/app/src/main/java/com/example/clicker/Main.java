package com.example.clicker;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class Main extends Activity
{
    private GameLoop thread;
    private World world;
    private DBHandler dbHandler;
    private int previousAmountOfTroops;
    private HeroFragment fragHero;
    private TroopsFragment fragTroops;
    private RewardFragment fragReward;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        world = new World();
        dbHandler = new DBHandler(this, null, null, 1);

        if(dbHandler.databaseExist())
        {
            world.setLevel(dbHandler.readWorldDatabase());
            world.CreateFoe();
            world.setHero(dbHandler.readHeroDatabase());
            world.getHero().setNextLevelExp((int) (10 * Math.pow(2, world.getHero().getLevel())));
            world.setTroops(dbHandler.readTroopsDatabase());
            previousAmountOfTroops = world.getTroops().size();

            if(world.getTroops().size() > 0)
            {
                world.setNextTroopCost((int) Math.pow(10, world.getTroops().size() + 1));

                world.setDisconnectTime(dbHandler.readWorldTimeDatabase());
                world.setEnterTime(System.currentTimeMillis() / 1000);
                world.getReward();
                world.getHero().setGold(world.getHero().getGold() + world.getPaid());

                fragReward = new RewardFragment();
                fragReward.setWorld(world);
                ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragReward);
                ft.commit();
            }
            else
            {
                fragHero = new HeroFragment();
                ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragHero);
                ft.commit();
            }
        }
        else
        {
            fragHero = new HeroFragment();
            ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragHero);
            ft.commit();
        }

        thread = new GameLoop();
        thread.setWorld(world, Main.this, this);
        thread.setRunning(true);
        thread.start();
    }

    public void toMenu(View view)
    {
        save();
        startActivity(new Intent(getApplicationContext(), Start.class));
    }

    public void toHero(View view)
    {
        fragHero = new HeroFragment();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragHero);
        ft.commit();
    }

    public void toTroops(View view)
    {
        fragTroops = new TroopsFragment();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragTroops);
        ft.commit();
        fragTroops.setWorld(world);
    }

    public void tapEnemy(View view)
    {
        world.getFoe().setHp(world.getFoe().getHp() - world.getHero().getDamage());

        if(world.getFoe().getHp() <= 0)
        {
            thread.CreateEnemy();
        }

        TextView enemyHp = (TextView) findViewById(R.id.EnemyHpNumber);
        enemyHp.setText(String.valueOf(world.getFoe().getHp()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        Button heroButton = (Button) findViewById(R.id.HeroButton);
        Button troopsButton = (Button) findViewById(R.id.TroopsButton);
        Button menuButton = (Button) findViewById(R.id.MenuButton);

        heroButton.setWidth(troopsButton.getWidth());
        menuButton.setWidth(troopsButton.getWidth());
    }

    public void HireTroop(View view)
    {
        if(world.getHero().getGold() >= world.getNextTroopCost())
        {
            world.getHero().setGold(world.getHero().getGold() - world.getNextTroopCost());
            world.getTroops().add(new Troop());
            world.setNextTroopCost((int) Math.pow(10, world.getTroops().size() + 1));
            toTroops(view);
        }
        else
        {
            Toast.makeText(Main.this, "Not enough gold. Need " + String.valueOf(world.getNextTroopCost()), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if(event.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch (event.getKeyCode())
            {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

   /* @Override
    protected void onPause()
    {
        save();
        super.onPause();

    }

    @Override
    protected void onStop()
    {
        save();
        super.onStop();
    }*/

    @Override
    protected void onDestroy()
    {
        save();
        super.onDestroy();
    }

    public void save()
    {
        thread.setRunning(false);
        world.setDisconnectTime(System.currentTimeMillis() / 1000);

        if(dbHandler.databaseExist())
        {
            dbHandler.updateWorld(world);
            dbHandler.updateHero(world);
            for(int i = 0; i < previousAmountOfTroops; i++)
            {
                dbHandler.updateTroops(world, i);
            }

            if(world.getTroops().size() > previousAmountOfTroops)
            {
                for(int i = previousAmountOfTroops; i < world.getTroops().size(); i++)
                {
                    dbHandler.fillTroopsDatabase(world, i);
                }
            }
        }
        else
        {
            dbHandler.fillWorldDatabase(world);
            dbHandler.fillHeroDatabase(world);
            for(int i = 0; i < world.getTroops().size(); i++)
            {
                dbHandler.fillTroopsDatabase(world, i);
            }
        }
        dbHandler.closeDB();
    }
}
