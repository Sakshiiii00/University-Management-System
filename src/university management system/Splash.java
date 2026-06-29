package university.management.system;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame implements Runnable {

    private Thread t;

    Splash() {
        setUndecorated(true); // Removes title bar for better splash effect

        // Load and scale image
        ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        add(image);

        setVisible(true);

        // Start animation thread
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            animateSplash();
            Thread.sleep(2000); // Show splash for 2 seconds
            setVisible(false);
            new Login(); // Open next frame
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateSplash() {
        int targetWidth = 1000, targetHeight = 700; // Final size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen dimensions
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int x = (screenWidth - targetWidth) / 2; // Center X position
        int y = (screenHeight - targetHeight) / 2; // Center Y position

        for (int i = 100; i <= targetWidth; i += 10) {
            int height = (i * targetHeight) / targetWidth;
            setBounds((screenWidth - i) / 2, (screenHeight - height) / 2, i, height);
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
