package com.example.clicker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "saves.db";

    private static final String TABLE_WORLD = "world";
    private static final String COLUMN_WORLD_ID = "_id";
    private static final String COLUMN_WORLD_LEVEL = "level";
    private static final String COLUMN_WORLD_TIME = "leaveTime";

    private static final String TABLE_TROOPS = "troops";
    private static final String COLUMN_TROOP_ID = "_id";
    private static final String COLUMN_TROOP_LEVEL = "level";
    private static final String COLUMN_TROOP_DAMAGE = "damage";
    private static final String COLUMN_TROOP_COST = "cost";
    //private static final String COLUMN_TROOP_NAME = "name";

    private static final String TABLE_HERO = "hero";
    private static final String COLUMN_HERO_ID = "_id";
    //private static final String COLUMN_HERO_NAME = "name";
    private static final String COLUMN_HERO_LEVEL = "level";
    private static final String COLUMN_HERO_DAMAGE = "damage";
    private static final String COLUMN_HERO_EXP = "exp";
    private static final String COLUMN_HERO_GOLD = "gold";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String worldQuery = "CREATE TABLE " + TABLE_WORLD + "( " +
                COLUMN_WORLD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WORLD_LEVEL + " INTEGER, " +
                COLUMN_WORLD_TIME + " LONG " +
                ");";
        db.execSQL(worldQuery);

        String heroQuery = "CREATE TABLE " + TABLE_HERO + "( " +
                COLUMN_HERO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HERO_LEVEL + " INTEGER, " +
                //COLUMN_HERO_NAME + " TEXT, " +
                COLUMN_HERO_DAMAGE + " INTEGER, " +
                COLUMN_HERO_EXP + " INTEGER, " +
                COLUMN_HERO_GOLD + " INTEGER " +
                ");";
        db.execSQL(heroQuery);

        String troopQuery = "CREATE TABLE " + TABLE_TROOPS + "( " +
                COLUMN_TROOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TROOP_LEVEL + " INTEGER, " +
                COLUMN_TROOP_DAMAGE + " INTEGER, " +
                COLUMN_TROOP_COST + " INTEGER " +
               // COLUMN_TROOP_NAME + " TEXT " +
                ");";
        db.execSQL(troopQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORLD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HERO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TROOPS);
        onCreate(db);
    }

    public void fillWorldDatabase(World world)
    {
        Log.d("FOE", "fillDatabase");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("FOE", "fillDatabase: level " + world.getLevel());
        values.put(COLUMN_WORLD_LEVEL, world.getLevel());
        values.put(COLUMN_WORLD_TIME, world.getDisconnectTime());
        db.insert(TABLE_WORLD, null, values);

        db.close();
    }

    public void fillHeroDatabase(World world)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HERO_LEVEL, world.getHero().getLevel());
        values.put(COLUMN_HERO_EXP, world.getHero().getExp());
        values.put(COLUMN_HERO_DAMAGE, world.getHero().getDamage());
        values.put(COLUMN_HERO_GOLD, world.getHero().getGold());
        db.insert(TABLE_HERO, null, values);

        db.close();
    }

    public void fillTroopsDatabase(World world, int i)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TROOP_LEVEL, world.getTroops().get(i).getLevel());
        values.put(COLUMN_TROOP_COST, world.getTroops().get(i).getCost());
        values.put(COLUMN_TROOP_DAMAGE, world.getTroops().get(i).getDamage());
        db.insert(TABLE_TROOPS, null, values);

        db.close();
    }

    public int updateWorld(World world)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORLD_LEVEL, world.getLevel());
        values.put(COLUMN_WORLD_TIME, world.getDisconnectTime());
        return db.update(TABLE_WORLD, values, null, null);
    }

    public int updateHero(World world)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HERO_LEVEL, world.getHero().getLevel());
        values.put(COLUMN_HERO_EXP, world.getHero().getExp());
        values.put(COLUMN_HERO_DAMAGE, world.getHero().getDamage());
        values.put(COLUMN_HERO_GOLD, world.getHero().getGold());
        return db.update(TABLE_HERO, values, null, null);
    }

    public int updateTroops(World world, int i)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TROOP_LEVEL, world.getTroops().get(i).getLevel());
        values.put(COLUMN_TROOP_DAMAGE, world.getTroops().get(i).getDamage());
        values.put(COLUMN_TROOP_COST, world.getTroops().get(i).getCost());
        return db.update(TABLE_TROOPS, values, COLUMN_TROOP_ID + " = ? ", new String[] { String.valueOf(i + 1)});
    }

    public int readWorldDatabase()
    {
        int level = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_WORLD + " WHERE 1" ,null);

        if(c.moveToFirst())
        {
            do
            {
                level = c.getInt(c.getColumnIndex(COLUMN_WORLD_LEVEL));
            }while(c.moveToNext());
        }

        c.close();
        return level;
    }

    public long readWorldTimeDatabase()
    {
        long time = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_WORLD + " WHERE 1" ,null);

        if(c.moveToFirst())
        {
            do
            {
                time = c.getInt(c.getColumnIndex(COLUMN_WORLD_TIME));
            }while(c.moveToNext());
        }

        c.close();
        return time;
    }

    public Hero readHeroDatabase()
    {
        Hero hero = new Hero();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_HERO + " WHERE 1" ,null);

        if(c.moveToFirst())
        {
            do
            {
                hero.setLevel(c.getInt(c.getColumnIndex(COLUMN_HERO_LEVEL)));
                hero.setDamage(c.getInt(c.getColumnIndex(COLUMN_HERO_DAMAGE)));
                hero.setExp(c.getInt(c.getColumnIndex(COLUMN_HERO_EXP)));
                hero.setGold(c.getInt(c.getColumnIndex(COLUMN_HERO_GOLD)));
            }while(c.moveToNext());
        }

        c.close();
        return hero;
    }

    public List<Troop> readTroopsDatabase()
    {
        World world = new World();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TROOPS + " WHERE 1", null);

        if(c.moveToFirst())
        {
            do
            {
                world.getTroops().add(new Troop(c.getInt(c.getColumnIndex(COLUMN_TROOP_LEVEL)),
                                                c.getInt(c.getColumnIndex(COLUMN_TROOP_DAMAGE)),
                                                c.getInt(c.getColumnIndex(COLUMN_TROOP_COST))));
            }while(c.moveToNext());
        }

        c.close();
        return world.getTroops();
    }

    public void closeDB()
    {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null && db.isOpen())
        {
            db.close();
        }

    }

    public boolean databaseExist()
    {
        File dbFile = new File("/data/data/com.example.clicker/databases/" + DATABASE_NAME);
        return dbFile.exists();
    }
}
