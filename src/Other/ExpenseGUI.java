package Other;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

import Font.FLoad;
import Frames.TotalAmountFrame;
import Source.FeelButton;
import Source.Warna;
import com.toedter.calendar.JDateChooser;


public class ExpenseGUI extends JFrame
{
    private ExpenseManager expenseManager;
    private DefaultListModel<String> listModel;
    private JList<String> expenseList;
    private JTextField descriptionField;
    private JTextField amountField;
    private JDateChooser dateChooser;

    public ExpenseGUI() throws IOException, FontFormatException {
        expenseManager = new ExpenseManager();
        expenseManager.loadFromFile("expenses.dat");

        //JFrame frame = new JFrame();

        setTitle("Manager Biaya");
        setSize(800, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src/Icons/iconutama.png");
        setIconImage(icon.getImage());

        listModel = new DefaultListModel<>();
        expenseList = new JList<>(listModel);
        updateExpenseList();

        Font custom1 = FLoad.loadFont("src/Font/Akira.otf", 14);

        JScrollPane scrollPane = new JScrollPane(expenseList);
        add(scrollPane, BorderLayout.CENTER);

        Dimension buttonSize = new Dimension(80, 40);
        Dimension buttonSize2 = new Dimension(40, 20);

        descriptionField = new JTextField();
        amountField = new JTextField();

        //=======[BUTTON AREA]=======//
        FeelButton addButton = new FeelButton();
        FeelButton editButton = new FeelButton();
        FeelButton deleteButton = new FeelButton();
        FeelButton totalAmountB = new FeelButton();

        addButton.setText("Add");
        editButton.setText("Edit");
        deleteButton.setText("Delete");
        totalAmountB.setText("Total");

        addButton.setPreferredSize(buttonSize);
        editButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        totalAmountB.setPreferredSize(buttonSize);

        //=======[Calendar]=======//
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MMMM-yyyy");
        dateChooser.setBackground(new Color(0x1ECBE1));

        //=========[Susunan Panel]========//
        JLabel descriptionLabel = new JLabel("Deskripsi :");
        JLabel amountLabel = new JLabel("Jumlah :");
        JLabel tanggalLabel = new JLabel("Tanggal :");
        JLabel totalAmountL = new JLabel("Total Biaya");

        descriptionLabel.setForeground(Color.WHITE);
        amountLabel.setForeground(Color.WHITE);
        tanggalLabel.setForeground(Color.WHITE);
        totalAmountL.setForeground(Color.WHITE);

        descriptionLabel.setFont(custom1);
        amountLabel.setFont(custom1);
        tanggalLabel.setFont(custom1);
        totalAmountL.setFont(custom1);

        //JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        Warna inputPanel = new Warna(new Color(0xEE0979), new Color(0xFF6A00));
        inputPanel.setLayout(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);

        inputPanel.add(amountLabel);
        inputPanel.add(amountField);

        inputPanel.add(tanggalLabel);
        inputPanel.add(dateChooser);

        inputPanel.add(addButton);
        inputPanel.add(editButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        Warna buttonPanel = new Warna(new Color(0xEE0979), new Color(0xFF6A00));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

        JPanel totalPanel = new JPanel();
        totalPanel.setBackground(new Color(0xFF6A00));

        //totalPanel.add(totalAmountL);
        totalPanel.add(totalAmountB);

        add(totalPanel, BorderLayout.EAST);


        //=========[Button Listener]========//
        totalAmountB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTotalAmountPopup();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editExpense();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExpense();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addExpense() {
        String description = descriptionField.getText();
        try {
            String amountText = amountField.getText();
            double amount = Double.parseDouble(amountText);

            Date date = dateChooser.getDate();
            date = getJam(date);

            Expense expense = new Expense(description, amount, date);
            expenseManager.addExpense(expense);

            updateExpenseList();
            clearFields();
            saveDataToFile();
        } catch (NumberFormatException e) {
            UIManager.put("OptionPane.background", new Color(0xEE0979));
            UIManager.put("Panel.background", new Color(0xEE0979));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("Button.background", Color.WHITE);
            UIManager.put("Button.foreground", Color.BLACK);

            ImageIcon errorIcon = new ImageIcon("src/Icons/error.png");
            Image fixImage = errorIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon useError = new ImageIcon(fixImage);

            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid untuk jumlah.", "Error", JOptionPane.ERROR_MESSAGE, useError);
            UIManager.put("OptionPane.background", UIManager.get("OptionPane.background"));
            UIManager.put("Panel.background", UIManager.get("Panel.background"));
        }
    }

    private Date getJam(Date date)
    {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private void editExpense() {
        int selectedIndex = expenseList.getSelectedIndex();

        if (selectedIndex != -1) {
            String description = descriptionField.getText();
            String amountText = amountField.getText();
            Date date = dateChooser.getDate();

            try {
                if (!amountText.isEmpty()) {
                    double amount = Double.parseDouble(amountText);

                    Expense expense = new Expense(description, amount, date);
                    expenseManager.editExpense(selectedIndex, expense);

                    updateExpenseList();
                    clearFields();
                    saveDataToFile();
                } else {
                    UIManager.put("OptionPane.background", new Color(0xEE0979));
                    UIManager.put("Panel.background", new Color(0xEE0979));
                    UIManager.put("OptionPane.messageForeground", Color.WHITE);
                    UIManager.put("Button.background", Color.WHITE);
                    UIManager.put("Button.foreground", Color.BLACK);

                    ImageIcon errorIcon = new ImageIcon("src/Icons/error.png");
                    Image fixImage = errorIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    ImageIcon useError = new ImageIcon(fixImage);

                    JOptionPane.showMessageDialog(this, "Masukkan angka yang valid untuk jumlah.", "Error", JOptionPane.ERROR_MESSAGE, useError);
                    UIManager.put("OptionPane.background", UIManager.get("OptionPane.background"));
                    UIManager.put("Panel.background", UIManager.get("Panel.background"));
                }
            } catch (NumberFormatException e) {

                UIManager.put("OptionPane.background", new Color(0xEE0979));
                UIManager.put("Panel.background", new Color(0xEE0979));
                UIManager.put("OptionPane.messageForeground", Color.WHITE);
                UIManager.put("Button.background", Color.WHITE);
                UIManager.put("Button.foreground", Color.BLACK);

                ImageIcon errorIcon = new ImageIcon("src/Icons/error.png");
                Image fixImage = errorIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                ImageIcon useError = new ImageIcon(fixImage);

                JOptionPane.showMessageDialog(this, "Masukkan angka yang valid untuk jumlah.", "Error", JOptionPane.ERROR_MESSAGE, useError);
                UIManager.put("OptionPane.background", UIManager.get("OptionPane.background"));
                UIManager.put("Panel.background", UIManager.get("Panel.background"));
            }
        }
    }

    private void deleteExpense() {
        int selectedIndex = expenseList.getSelectedIndex();

        if (selectedIndex != -1) {
            expenseManager.deleteExpense(selectedIndex);

            updateExpenseList();
            clearFields();
            saveDataToFile();
        }
    }

    private void updateExpenseList() {
        listModel.clear();
        double totalAmount = 0.0;

        for (Expense expense : expenseManager.getExpenses()) {
            listModel.addElement(expense.getDescription() + " | Rp. " + expense.getAmount() + " | Tanggal: " + expense.getDate());
            totalAmount += expense.getAmount();
        }
    }

    private void clearFields() {
        descriptionField.setText("");
        amountField.setText("");
    }

    private void saveDataToFile() {
        expenseManager.saveToFile("expenses.dat");
    }

    private void showTotalAmountPopup()
    {
        double totalAmount = expenseManager.getTotalAmount();
        new TotalAmountFrame(this, totalAmount);
    }
}
