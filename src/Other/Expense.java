package Other;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String description;
    private double amount;
    private Date date;

    public Expense(String description, double amount, Date date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate()
    {
        return date;
    }
}
