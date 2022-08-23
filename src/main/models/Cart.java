package src.main.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.text.PlainDocument;

public class Cart {
    ArrayList<Item> items;

    public Cart() {
        this.items = new ArrayList<Item>();
    }

    public Item getItem(int index) {
        return new Item(this.items.get(index));
    }

    public void setItem(int index, Item item) {
        this.items.set(index, new Item(item));
    }

    public void addItem(Item item) {
        items.add(new Item(item));
    }

    public void removeItem(String name) {
        if (items.size() < 1) {
            throw new IllegalStateException("Can't remove if cart is empty.");
        }
        this.items.removeIf((item) -> item.getName().equalsIgnoreCase(name));

        // for (int i = 0; i < items.size(); i++) {
        // if (items.get(i).getName().equalsIgnoreCase(name)) {
        // items.remove(items.get(i));
        // }
        // }
    }

    public boolean itemIncluded(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(item)) {
                return true;
            }
        }
        return false;
    }

    public double getSubtotal() {
        // double temp = 0;
        // for (int i = 0; i < items.size(); i++) {
        // temp += items.get(i).getPrice();
        // }
        // return temp;

        return items.stream()
                .mapToDouble(x -> x.getPrice()).sum();
    }

    public double getTax(double subtotal) {
        return round((subtotal * 0.13), 3);
    }

    public double getTotal(double tax, double subtotal) {
        return ((tax * subtotal) + subtotal);
    }

    public String checkout() {
        if (items.size() < 1) {
            throw new IllegalStateException("Can't check out if cart is empty.");
        }
        return "\tRECEIPT\n\n" +
                "\tSubtotal: $" + getSubtotal() + "\n" +
                "\tTax: $" + getTax(getSubtotal()) + "\n" +
                "\tTotal: $" + getTotal(getTax(getSubtotal()), getSubtotal()) + getTax(getSubtotal()) + "\n";
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < this.items.size(); i++) {
            temp += this.items.get(i).toString();
            temp += "\n";
        }
        return temp;
    }

    public void clear() {
        items.clear();
    }

    // Rounding double
    public double round(double value, int palces) {
        if (palces < 0) {
            throw new IllegalArgumentException("Placing value number can't be lower than 0");
        }

        BigDecimal bD = BigDecimal.valueOf(value);
        bD = bD.setScale(palces, RoundingMode.HALF_UP);
        return bD.doubleValue();
    }

}
