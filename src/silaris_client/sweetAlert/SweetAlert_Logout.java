package silaris_client.sweetAlert;

import javax.swing.*;
import java.awt.*;

public class SweetAlert_Logout extends JDialog {

    private float opacityLevel = 0f;
    private float iconScale = 0f;
    private float iconAlpha = 0f;

    public SweetAlert_Logout(Frame parent, Runnable onConfirm) {

        super(parent, false);
        setUndecorated(true);
        setSize(360, 230);
        setLocationRelativeTo(parent);
        setBackground(new Color(0,0,0,0));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, opacityLevel));
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ================= ICON =================
        ImageIcon iconImg = new ImageIcon(
                getClass().getResource("/silaris_client_assets/Logout.png"));
        Image image = iconImg.getImage();

        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                g2.setComposite(
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, iconAlpha));

                int size = (int) (70 * iconScale);
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                g2.drawImage(image, x, y, size, size, this);
            }
        };

        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(100, 90));

        // ================= TEXT =================
        JLabel text = new JLabel(
                "<html><center>"
                + "Apakah Anda yakin ingin keluar dari sistem?<br>"
                + "Semua sesi aktif akan diakhiri."
                + "</center></html>",
                SwingConstants.CENTER);

        text.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // ================= BUTTON =================
        JButton btnCancel = new JButton("Batal");
        btnCancel.setFocusPainted(false);
        btnCancel.setBackground(new Color(149,165,166));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancel.setPreferredSize(new Dimension(110, 35));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnLogout = new JButton("Ya, Logout");
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(new Color(231,76,60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogout.setPreferredSize(new Dimension(110, 35));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancel.addActionListener(e -> dispose());

        btnLogout.addActionListener(e -> {
            dispose();
            onConfirm.run();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnCancel);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnLogout);

        panel.add(iconPanel, BorderLayout.NORTH);
        panel.add(text, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        startAnimation(iconPanel);
    }

    private void startAnimation(JPanel iconPanel) {

        Timer timer = new Timer(15, null);

        timer.addActionListener(e -> {

            // FADE IN + ICON BOUNCE
            opacityLevel += 0.08f;
            iconAlpha += 0.08f;
            iconScale += 0.1f;

            if (iconScale > 1.1f)
                iconScale = 1.05f;

            if (opacityLevel >= 1f)
                opacityLevel = 1f;

            if (iconAlpha >= 1f)
                iconAlpha = 1f;

            if (iconScale >= 1f)
                iconScale = 1f;

            if (opacityLevel == 1f && iconScale == 1f)
                timer.stop();

            iconPanel.repaint();
            repaint();
        });

        timer.start();
    }

    public static void show(Frame parent, Runnable onConfirm) {
        new SweetAlert_Logout(parent, onConfirm).setVisible(true);
    }
}