package com.example.clicker;

public class Enemy
{
    //private String name;
    private int hp;
    private int exp;
    private int gold;

    public Enemy(int level)
    {
        this.hp = (int) (Math.pow(1.1, level) * 10);
        this.exp = hp / 10;
        if(level < 10)
        {
            this.gold = hp / 10;
        }
        else
        {
            this.gold = hp / 15;
        }
    }

    public int getHp()
    {
        return hp;
    }

    public int getExp()
    {
        return exp;
    }

    public int getGold()
    {
        return gold;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }

    public void setExp(int exp)
    {
        this.exp = exp;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
    }

}
