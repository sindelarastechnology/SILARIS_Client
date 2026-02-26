/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.panel;

import javax.swing.*;
import java.awt.*;

public class SweetAlert extends JDialog {

    private float opacityLevel = 0f;
    private float iconScale = 0f;
    private float iconAlpha = 0f;

    private boolean closing = false;

    public SweetAlert(Frame parent, String message) {
        super(parent, false);
        setUndecorated(true);
        setSize(360, 200);
        setLocationRelativeTo(parent);
        setBackground(new Color(0,0,0,0));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacityLevel));
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // LOAD ICON
        ImageIcon iconImg = new ImageIcon(
                getClass().getResource("/silaris_client_assets/icon_login.png"));
        Image image = iconImg.getImage();

        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, iconAlpha));

                int size = (int) (70 * iconScale);
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                g2.drawImage(image, x, y, size, size, this);
            }
        };

        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(100, 90));

        JLabel text = new JLabel(
                "<html><center>" + message + "</center></html>",
                SwingConstants.CENTER);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        panel.add(iconPanel, BorderLayout.NORTH);
        panel.add(text, BorderLayout.CENTER);
        add(panel);

        startAnimation(iconPanel);
    }

    private void startAnimation(JPanel iconPanel) {

        Timer timer = new Timer(15, null);

        timer.addActionListener(e -> {

            if (!closing) {
                // FADE IN + ICON GROW + BOUNCE EFFECT
                opacityLevel += 0.08f;
                iconAlpha += 0.08f;
                iconScale += 0.1f;

                // Bounce kecil (modern feel)
                if (iconScale > 1.1f) {
                    iconScale = 1.05f;
                }

                if (opacityLevel >= 1f) opacityLevel = 1f;
                if (iconAlpha >= 1f) iconAlpha = 1f;
                if (iconScale >= 1f) iconScale = 1f;

                if (opacityLevel == 1f && iconScale == 1f) {
                    // Tunggu 1.8 detik sebelum close
                    new Timer(1800, ev -> closing = true).start();
                }
            } else {
                // FADE OUT + ICON SHRINK
                opacityLevel -= 0.08f;
                iconAlpha -= 0.08f;
                iconScale -= 0.08f;

                if (opacityLevel <= 0f) {
                    opacityLevel = 0f;
                    timer.stop();
                    dispose();
                }
            }

            iconPanel.repaint();
            repaint();
        });

        timer.start();
    }
}