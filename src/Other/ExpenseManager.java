package Other;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager
{
    private List<Expense> expenses;

    public ExpenseManager() {
        expenses = new ArrayList<>();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void editExpense(int index, Expense expense) {
        expenses.set(index, expense);
    }

    public void deleteExpense(int index) {
        expenses.remove(index);
    }

    public void saveToFile(String fileName)
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String fileName)
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double getTotalAmount()
    {
        double totalAmount = 0.0;
        for (Expense expense : expenses) {
            totalAmount += expense.getAmount();
        }
        return totalAmount;
    }
}
