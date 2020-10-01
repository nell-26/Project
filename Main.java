//package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    // колекція для збереження продуктів зчитаних з клавіатури
    static ArrayList<Product> products = new ArrayList<>();

    // створюємо об'єкт долара
    static Currency dollar = new Currency("Долар", 28.2f);

    public static void main(String[] args) {
        int count = 0;

	    System.out.println("введіть кількість продуктів які хотіли б додати");
	    count = new Scanner(System.in).nextInt();

        // зчитуємо продукти з консолі
	    readProductsArray(count);

	    // друкуємо продукти в довільному порядку
	    printProducts(products);

	    // друкуємо найдешевший та найдорожчий продукт
	    getProductsInfo(products);

        System.out.println("Сортуємо продукти по ціні: \n");
	    // сортуємо продукти по ціні
        sortProductsByPrice(products);

        // друкуємо посортовані продукти
        printProducts(products);

        System.out.println("Сортуємо продукти по кількості на складі: \n");
        // сортуємо продукти по кількості на складі
        sortProductByQuantity(products);

        // друкуємо посортовані продукти
        printProducts(products);
    }

    static void readProductsArray(int numberOfProducts) {
        String name = "";
        float cost = 0.0f;
        int quantity = 0;
        String producer = "";
        float weight = 0.0f;

        String helpText = "ви задаєте дані про продукт #";

        for(int i = 0; i < numberOfProducts; i++) {
            System.out.println(helpText + (i+1));

            System.out.println("Введіть назву продукту");
            name = new Scanner(System.in).nextLine();

            System.out.println("Введіть ціну продукту");
            cost = new Scanner(System.in).nextFloat();

            System.out.println("Введіть кількість продукту");
            quantity = new Scanner(System.in).nextInt();

            System.out.println("Введіть виробника продукту продукту");
            producer = new Scanner(System.in).nextLine();

            System.out.println("Введіть вагу продукту");
            weight = new Scanner(System.in).nextFloat();

            products.add(new Product(name, cost, quantity, producer, weight, dollar));
        }

        System.out.println("Продукти були додані \n");
    }

    static void printProduct(Product product) {
        System.out.println(product.toString());
    }

    static void printProducts(ArrayList<Product> products) {
        for(Product temp : products) {
            printProduct(temp);
            System.out.println();
        }
    }

    static void getProductsInfo(ArrayList<Product> products) {
        ArrayList<Product> p = products;
        Collections.sort(p, new ProductPriceComparator());

        System.out.print("Найдешевший продукт: \n");
        printProduct(p.get(0));
        System.out.println();

        System.out.print("Найдорожчий продукт: \n");
        printProduct(p.get(products.size() - 1));
        System.out.println();
    }

    static void sortProductsByPrice(ArrayList<Product> products) {
        Collections.sort(products, new ProductPriceComparator());
    }

    static void sortProductByQuantity(ArrayList<Product> products) {
        Collections.sort(products, new ProductQuantityComparator());
    }
}

class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        if(o1.getCost() < o2.getCost()) {
            return -1;
        } else if(o1.getCost() > o2.getCost()) {
            return 1;
        } else {
            return 0;
        }
    }
}

class ProductQuantityComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        if(o1.getQuantity() < o2.getQuantity()) {
            return -1;
        } else if(o1.getQuantity() > o2.getQuantity()) {
            return 1;
        } else {
            return 0;
        }
    }
}

class Currency {
    private String name;
    private float exRate;

    public Currency() {
    }

    public Currency(String name, float exRate) {
        this.name = name;
        this.exRate = exRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getExRate() {
        return exRate;
    }

    public void setExRate(float exRate) {
        this.exRate = exRate;
    }
}

class Product {
    // ім'я продукту
    private String name;
    // ціна за продукт (одиниць певної валюти)
    private float cost;
    // кількість продуктів на складі
    private int quantity;
    // виробник продукту
    private String producer;
    // вага продукту
    private float weight;
    // валюта за яку можна купити даний продукт
    private Currency currency;

    public Product() {
    }

    public Product(String name, float cost, int quantity, String producer, float weight, Currency currency) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.producer = producer;
        this.weight = weight;
        this.currency = currency;
    }

    // гетери та сетери
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    // повертаємо ціну валюти в гривнях помножену на ціну продукту
    public float getPriceInUAH() {
        return this.currency.getExRate() * cost;
    }

    // повертаємо ціну валюти в гривнях помножену на ціну продукту та на кількість продукту(загальна ціна всіх продуктів в гривнях)
    public float getTotalPriceInUAH() {
        return this.currency.getExRate() * this.cost * this.quantity;
    }

    // повертаємо загальну вагу всіх продуктів
    public float getTotalWeight() {
        return this.weight * this.quantity;
    }

    @Override
    public String toString() {
        return "Назва продукту: " + this.name +
                "\nЦіна продукту: " + this.cost +
                "\nКількість продуктів на складі: " + this.quantity +
                "\nВиробник продуктів: " + this.producer +
                "\nВага продукту: " + this.weight +
                "\nВалюта продукту: " + this.currency.getName();
    }
}

