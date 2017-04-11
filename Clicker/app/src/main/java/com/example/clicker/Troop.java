package com.example.clicker;

public class Troop
{
    //private String name;
    private int level;
    private int damage;
    private int cost;

    public Troop()
    {
        this.level = 1;
        this.damage = 1;
        this.cost = 10;
    }

    public Troop(int level, int damage, int cost)
    {
        this.level = level;
        this.damage = damage;
        this.cost = cost;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getDamage()
    {
        return damage;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }
}
