package com.example.clicker;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Start extends AppCompatActivity {

    private FragmentTransaction ft;
    private ClearDataFragment fragClearData;
    private StartButtonsFragment fragStartButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        fragStartButtons = new StartButtonsFragment();
        ft = getFragmentManager().beginTransaction();
        ft.add(R.id.start_container, fragStartButtons);
        ft.commit();

    }

    public void startGame(View view)
    {
        startActivity(new Intent(getApplicationContext(), Main.class));
    }

    public void clearData(View view)
    {
        fragClearData = new ClearDataFragment();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.start_container, fragClearData);
        ft.commit();
    }

    public void accept(View view)
    {
        File dbFile = new File("/data/data/com.example.clicker/databases/saves.db");
        dbFile.delete();
        Toast.makeText(Start.this, "All data was erase", Toast.LENGTH_LONG).show();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.start_container, fragStartButtons);
        ft.commit();
    }

    public void decline(View view)
    {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.start_container, fragStartButtons);
        ft.commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        Button startButton = (Button) findViewById(R.id.StartButton);
        Button clearDataButton = (Button) findViewById(R.id.ClearDataButton);

        startButton.setWidth(clearDataButton.getWidth());
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
}
