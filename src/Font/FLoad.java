package Font;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FLoad
{
    public static Font loadFont(String fontPath, float size)
    {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(fontPath));

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            customFont = customFont.deriveFont(size);

            return customFont;
        } catch (Exception e) {
            e.printStackTrace();
            // Return a default font in case of an error
            return new Font("Arial", Font.BOLD, 12);
        }
    }
}
