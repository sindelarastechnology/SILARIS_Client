package silaris_client.sweetAlert;

import javax.swing.*;
import java.awt.*;

public class SweetAlert_login extends JDialog {

    private float opacityLevel = 0f;
    private float iconScale = 0f;
    private float iconAlpha = 0f;
    private boolean closing = false;

    public SweetAlert_login(Frame parent, String message,
                            String iconPath, Color accentColor,
                            boolean showButton,
                            Runnable onOk) {

        super(parent, true); // modal
        setUndecorated(true);
        setSize(380, showButton ? 240 : 200);
        setLocationRelativeTo(parent);
        setAlwaysOnTop(true);
        setBackground(new Color(0,0,0,0));

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, opacityLevel));

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, getWidth(), 6, 25, 25);
            }
        };

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ================= ICON =================
        Image image = null;
        java.net.URL url = getClass().getResource(iconPath);
        if (url != null) image = new ImageIcon(url).getImage();

        Image finalImage = image;

        JPanel iconPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalImage == null) return;

                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, iconAlpha));

                int size = (int) (70 * iconScale);
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                g2.drawImage(finalImage, x, y, size, size, this);
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

        // ================= BUTTON (ONLY FOR SUCCESS) =================
        if (showButton) {

            JButton btnOk = new JButton("OK");
            btnOk.setFocusPainted(false);
            btnOk.setBackground(accentColor);
            btnOk.setForeground(Color.WHITE);
            btnOk.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnOk.setPreferredSize(new Dimension(110, 35));
            btnOk.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnOk.addActionListener(e -> {
                dispose();
                if (onOk != null) onOk.run();
            });

            JPanel btnPanel = new JPanel();
            btnPanel.setOpaque(false);
            btnPanel.add(btnOk);

            panel.add(btnPanel, BorderLayout.SOUTH);
        }

        add(panel);

        startAnimation(iconPanel, showButton);
    }

    private void startAnimation(JPanel iconPanel, boolean showButton) {

        Timer timer = new Timer(15, null);

        timer.addActionListener(e -> {

            if (!closing) {

                if(opacityLevel < 1f) opacityLevel += 0.08f;
                if(iconAlpha < 1f) iconAlpha += 0.08f;
                if(iconScale < 1f) iconScale += 0.1f;

                if (opacityLevel > 1f) opacityLevel = 1f;
                if (iconAlpha > 1f) iconAlpha = 1f;
                if (iconScale > 1f) iconScale = 1f;

                // Auto close only if NO button
                if (!showButton && opacityLevel == 1f) {
                    new Timer(1500, ev -> closing = true).start();
                }

            } else {

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

    // ================= METHODS =================

    public static void success(Frame parent, String message, Runnable onOk) {
        new SweetAlert_login(
                parent,
                message,
                "/silaris_client_assets/Login_Success.png",
                new Color(46, 204, 113),
                true,
                onOk
        ).setVisible(true);
    }

    public static void error(Frame parent, String message) {
        new SweetAlert_login(
                parent,
                message,
                "/silaris_client_assets/Login_Error.png",
                new Color(231, 76, 60),
                false,
                null
        ).setVisible(true);
    }

    public static void warning(Frame parent, String message) {
        new SweetAlert_login(
                parent,
                message,
                "/silaris_client_assets/Login_Warning.png",
                new Color(241, 196, 15),
                false,
                null
        ).setVisible(true);
    }
}