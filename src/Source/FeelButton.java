package Source;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FeelButton extends JButton {
    private boolean hover;
    private Color fill;
    private Color line;
    private Color lineOrigin;
    private Color lineHover;
    private Color originallFill;
    private Color hoverFIll;
    private Color clickFill;
    private int strokeWidth;

    public FeelButton() {
        originallFill = new Color(254, 178, 38);
        hoverFIll = new Color(255, 165, 0);
        clickFill = new Color(255, 176, 31);

        lineOrigin = new Color(115, 115, 115);
        lineHover = new Color(0, 0, 0);

        strokeWidth = 2;

        fill = originallFill;
        line = lineOrigin;

        setOpaque(false);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBackground(originallFill);
        setForeground(Color.WHITE);

        //Mouse Listener
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseExited(MouseEvent e)
            {
                fill = originallFill;
                //line = lineOrigin;

                hover = false;
            }

            public void mouseEntered(MouseEvent e)
            {
                fill = hoverFIll;
                //line = lineHover;

                hover = true;
            }

            public void mouseReleased(MouseEvent e)
            {
                if(hover)
                {
                    fill = hoverFIll;
                    //line = lineHover;
                }
                else
                {
                    fill = originallFill;
                    //line = lineOrigin;
                }
            }

            public void mousePressed(MouseEvent e)
            {
                fill = clickFill;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if(!isOpaque())
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int s = strokeWidth;
            int w = getWidth() - (2 * s);
            int h = getHeight() - (2 * s);

            //render Bg
            g2d.setColor(fill);
            g2d.fillRoundRect(s, s, w, h, h, h);

            //render St
            /*g2d.setStroke(new BasicStroke(s));
            g2d.setColor(line);
            g2d.fillRoundRect(s, s, w, h, h, h);*/
        }
        super.paintComponent(g);
    }

}
