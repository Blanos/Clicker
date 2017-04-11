package com.example.clicker;

public class Hero
{
    //private String name;
    private int level;
    private int damage;
    private int exp;
    private int nextLevelExp;
    private int gold;


    public Hero()
    {
        this.level = 1;
        this.exp = 0;
        this.damage = level;
        this.nextLevelExp = (int) (10 * Math.pow(2, level));
        this.gold = 0;
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

    public int getExp()
    {
        return exp;
    }

    public void setExp(int exp)
    {
        this.exp = exp;
    }

    public void setNextLevelExp(int nextLevelExp)
    {
        this.nextLevelExp = nextLevelExp;
    }

    public int getNextLevelExp()
    {
        return nextLevelExp;
    }

    public int getGold()
    {
        return gold;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
    }


    public void levelUp(int restExp)
    {
        this.level = level + 1;
        this.damage = level;
        this.exp = restExp;
        this.nextLevelExp = (int) (10 * Math.pow(2, level));
                //Math.pow(level * 10, 2);
    }

}
