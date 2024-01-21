package Source;

import javax.swing.*;
import java.awt.*;

public class Warna extends JPanel
{
    private Color color1;
    private Color color2;

    public Warna(Color color1, Color color2)
    {
        this.color1 = color1;
        this.color2 = color2;
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        //Color color1 = new Color(0xEE0979);
        //Color color2 = new Color(0xFF6A00);

        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, width, height, color2);
        g2d.setPaint(gradientPaint);

        g2d.fillRect(0, 0, width, height);
    }
}
