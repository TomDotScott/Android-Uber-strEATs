package com.example.mobileandgamingdevices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity
{
    private Game m_game;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        m_game = new Game(this);

        setContentView(m_game);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        m_game.pause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    public void onBackPressed()
    {
        // Stop the app from closing when the back button is pressed
        // super.onBackPressed();
    }
}