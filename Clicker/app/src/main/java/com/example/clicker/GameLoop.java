package com.example.clicker;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameLoop extends Thread
{
    private boolean running;
    private TextView worldLevel;
    private TextView enemyHp;
    private TextView amountOfGold;
    private long beginTime;
    private long sleepTime;
    private final int FRAME_TIME = 1000;
    private World world;
    private Activity activity;
    private Context context;
    private int restExp;
    private ImageView enemyImage;
    private Random rand = new Random();

    public void setWorld(World world, Context context, Activity activity)
    {
        this.world = world;
        this.context = context;
        this.activity = activity;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    public void run()
    {
        while(running)
        {
            beginTime = System.currentTimeMillis();

            Update();
            Render();

            sleepTime = FRAME_TIME - (System.currentTimeMillis() - beginTime);
            if(sleepTime > 0)
            {
                try
                {
                    sleep(sleepTime);
                }
                catch (InterruptedException e)
                {

                }
            }
        }
    }

    public void Render()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                worldLevel = (TextView) activity.findViewById(R.id.EnemyLevelNumber);
                worldLevel.setText(String.valueOf(world.getLevel()));

                enemyHp = (TextView) activity.findViewById(R.id.EnemyHpNumber);
                enemyHp.setText(String.valueOf(world.getFoe().getHp()));

                amountOfGold = (TextView) activity.findViewById(R.id.GoldNumber);
                amountOfGold.setText(String.valueOf(world.getHero().getGold()));

                if(activity.getFragmentManager().findFragmentById(R.id.fragment_container) instanceof HeroFragment)
                {
                    TextView heroExp = (TextView) activity.findViewById(R.id.HeroExpNumber);
                    heroExp.setText(String.valueOf(world.getHero().getExp()));

                    TextView heroNextExp = (TextView) activity.findViewById(R.id.HeroNextExpNumbers);
                    heroNextExp.setText(String.valueOf(world.getHero().getNextLevelExp()));

                    TextView heroDamage = (TextView) activity.findViewById(R.id.HeroDamageNumber);
                    heroDamage.setText(String.valueOf(world.getHero().getDamage()));

                    TextView heroLevel = (TextView) activity.findViewById(R.id.HeroLevelNumber);
                    heroLevel.setText(String.valueOf(world.getHero().getLevel()));
                }

                if(activity.getFragmentManager().findFragmentById(R.id.fragment_container) instanceof TroopsFragment)
                {
                    TextView nextTroopCost = (TextView) activity.findViewById(R.id.NextTroopCostNumber);
                    nextTroopCost.setText(String.valueOf(world.getNextTroopCost()) + " gold");
                }
            }
        });
    }

    public void Update()
    {
        for(int i = 0; i < world.getTroops().size(); i++)
        {
            world.getFoe().setHp(world.getFoe().getHp() - world.getTroops().get(i).getDamage());
            if(world.getFoe().getHp() <= 0)
            {
                break;
            }
        }

        if(world.getHero().getExp() >= world.getHero().getNextLevelExp() )
        {
            restExp = world.getHero().getExp() - world.getHero().getNextLevelExp();
            world.getHero().levelUp(restExp);
        }

        if (world.getFoe() == null || world.getFoe().getHp() <= 0)
        {
            CreateEnemy();
        }
    }

    public void CreateEnemy()
    {
        world.getHero().setExp(world.getHero().getExp() + world.getFoe().getExp());
        world.getHero().setGold(world.getHero().getGold() + world.getFoe().getGold());
        world.increaseLevel();
        world.CreateFoe();

        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                worldLevel = (TextView) activity.findViewById(R.id.EnemyLevelNumber);
                worldLevel.setText(String.valueOf(world.getLevel()));

                amountOfGold = (TextView) activity.findViewById(R.id.GoldNumber);
                amountOfGold.setText((String.valueOf(world.getHero().getGold())));

                if(activity.getFragmentManager().findFragmentById(R.id.fragment_container) instanceof HeroFragment)
                {
                    TextView heroExp = (TextView) activity.findViewById(R.id.HeroExpNumber);
                    heroExp.setText(String.valueOf(world.getHero().getExp()));
                }

                enemyImage = (ImageView) activity.findViewById(R.id.EnemyImage);
                int randEnemy = rand.nextInt(5);
                Log.d("FOE", "rand: " + randEnemy);
                if(randEnemy == 0)
                {
                    enemyImage.setImageResource(R.drawable.foe1);
                }
                else if(randEnemy == 1)
                {
                    enemyImage.setImageResource(R.drawable.foe2);
                }
                else if(randEnemy == 2)
                {
                    enemyImage.setImageResource(R.drawable.foe3);
                }
                else if(randEnemy == 3)
                {
                    enemyImage.setImageResource(R.drawable.foe4);
                }
                else if(randEnemy == 4)
                {
                    enemyImage.setImageResource(R.drawable.foe5);
                }
            }
        });
    }

}
