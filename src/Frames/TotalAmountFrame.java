package Frames;

import Source.FeelButton;
import Source.Warna;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TotalAmountFrame extends JDialog {

    public TotalAmountFrame(JFrame parent, double totalAmount)
    {

        super(parent, "Total Biaya", true);

        setSize(300, 150);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel totalLabel = new JLabel("Total Biaya: Rp. " + totalAmount);
        totalLabel.setHorizontalAlignment(JLabel.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));

        add(totalLabel, BorderLayout.CENTER);

        Dimension buttonSize = new Dimension(60, 30);

        FeelButton closeButton = new FeelButton();
        closeButton.setText("Close");
        closeButton.setPreferredSize(buttonSize);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        //JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Warna buttonPanel = new Warna(new Color(0xEE0979), new Color(0xFF6A00));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
