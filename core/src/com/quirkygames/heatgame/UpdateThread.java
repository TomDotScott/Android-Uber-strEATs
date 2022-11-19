package com.quirkygames.heatgame;

public class UpdateThread extends Thread
{
    private static final int MAXIMUM_UPDATES_PER_SECOND = 60;
    private static final long TARGET_UPDATE_PERIOD = 1000 / MAXIMUM_UPDATES_PER_SECOND;

    private boolean m_isRunning = false;

    private GdxGame m_game;

    // TODO: private double m_averageFPS;
    private double m_averageUPS;

    public UpdateThread(GdxGame game)
    {
        m_game = game;
    }

    public double getAverageUPS()
    {
        return m_averageUPS;
    }

    // TODO: public double getAverageFPS()
    //{
    //    return m_averageFPS;
    //}

    public void startLoop()
    {
        m_isRunning = true;
        start();
    }

    @Override
    public void run()
    {
        super.run();

        // TODO: UPDATE FRAME COUNT NOW WE'RE USING GLES
        int updateCount = 0;

        long startTime = System.currentTimeMillis();
        long elapsedTime;
        long sleepTime;

        // The game loop itself
        while (m_isRunning)
        {
            try
            {
                // Update objects from Game
                m_game.update();
                updateCount++;
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = ((long) updateCount * TARGET_UPDATE_PERIOD) - elapsedTime;
            if (sleepTime > 0)
            {
                try
                {
                    sleep(sleepTime);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            // Skip frames to keep up with the target UPS
            while (sleepTime < 0 && updateCount < MAXIMUM_UPDATES_PER_SECOND - 1)
            {
                m_game.update();
                updateCount++;

                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = ((long) updateCount * TARGET_UPDATE_PERIOD) - elapsedTime;
            }

            // If we have hit a new second, calculate the new averages!
            if (elapsedTime >= 1000l)
            {
                // TODO: CALCULATE FPS WITH OPENGL m_averageFPS = frameCount / (0.001 * elapsedTime);
                m_averageUPS = updateCount / (0.001 * elapsedTime);

                // Reset the counters for the next second...
                updateCount = 0;

                startTime = System.currentTimeMillis();
            }
        }
    }

    public void stopLoop()
    {
        m_isRunning = false;
        // Wait for the thread to join...
        try
        {
            join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}