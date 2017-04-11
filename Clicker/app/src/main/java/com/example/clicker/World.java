package com.example.clicker;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class World
{
    private List<Troop> troops;
    private Enemy foe;
    private int level;
    private Hero hero;
    private int nextTroopCost;
    private long disconnectTime;
    private long enterTime;
    private int paid;

    public World()
    {
        this.level = 0;
        this.nextTroopCost = 10;
        CreateArray();
        CreateFoe();
        CreateHero();
    }

    private void CreateArray()
    {
        troops = new ArrayList<Troop>();
    }

    public void CreateFoe()
    {
        foe = new Enemy(level);
    }

    public Enemy getFoe()
    {
        return foe;
    }

    public List<Troop> getTroops()
    {
        return troops;
    }

    public void CreateHero()
    {
        hero = new Hero();
    }

    public Hero getHero()
    {
        return hero;
    }

    public int getLevel()
    {
        return level;
    }

    public void increaseLevel()
    {
        level = level + 1;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setHero(Hero hero)
    {
        this.hero = hero;
    }

    public void setTroops(List<Troop> troops)
    {
        this.troops = troops;
    }

    public int getNextTroopCost()
    {
        return nextTroopCost;
    }

    public void setNextTroopCost(int nextTroopCost)
    {
        this.nextTroopCost = nextTroopCost;
    }

    public void setDisconnectTime(long disconnectTime)
    {
        this.disconnectTime = disconnectTime;
    }

    public long getDisconnectTime()
    {
        return disconnectTime;
    }

    public long getEnterTime()
    {
        return enterTime;
    }

    public void setEnterTime(long enterTime)
    {
        this.enterTime = enterTime;
    }

    public int getPaid()
    {
        return paid;
    }

    public void setPaid(int paid)
    {
        this.paid = paid;
    }

    public void getReward()
    {
        long payTime = (enterTime - disconnectTime) / 60;

        if( payTime >= 5)
        {
            paid = (int) (payTime / 5 * (foe.getGold() / 10) * troops.size());
        }
    }
}
