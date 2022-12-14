package com.example.mobileandgamingdevices;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class DeliveryTarget
{
    private String m_name;
    private RectF m_collider;
    private float m_opacity;
    private final float m_glowRate = 0.02f;

    public enum eTargetType
    {
        Restaurant,
        DropOff
    }

    private eTargetType m_targetType;

    public final static int RESTAURANT_COLOUR = 0xFFF3FC74;
    public final static int DROP_OFF_COLOUR = 0xFF74FC92;

    public DeliveryTarget(String name, RectF collider, eTargetType targetType)
    {
        m_name = name;
        m_collider = collider;
        m_targetType = targetType;
    }

    public void update()
    {
        m_opacity += m_glowRate;
        if(m_opacity >= 720)
        {
            m_opacity = 0f;
        }
    }

    public void draw(Canvas canvas, GameDisplay gd)
    {
        Paint paint = new Paint();

        if (m_targetType == eTargetType.Restaurant)
        {
            paint.setColor(RESTAURANT_COLOUR);
        } else
        {
            paint.setColor(DROP_OFF_COLOUR);
        }

        paint.setStyle(Paint.Style.FILL);

        paint.setAlpha(Math.abs((int)(Math.sin(m_opacity) * 200)));

        Vector2 topLeft = gd.worldToScreenSpace(new Vector2(m_collider.left, m_collider.top));
        Vector2 bottomRight = gd.worldToScreenSpace(new Vector2(m_collider.right, m_collider.bottom));

        canvas.drawRoundRect(
                topLeft.x.floatValue(),
                topLeft.y.floatValue(),
                bottomRight.x.floatValue(),
                bottomRight.y.floatValue(),
                30,
                30,
                paint
        );
    }

    public boolean checkCollision(Player player)
    {
        return new RectF(m_collider).intersect(player.getCollider());
    }

    public String getName()
    {
        return m_name;
    }

    public Vector2 getCentre()
    {
        return new Vector2(m_collider.centerX(), m_collider.centerY());
    }
}
