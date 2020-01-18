package fr.istic.prgr1.exams;

import java.util.HashMap;

public class StoreStocks extends HashMap<String, Stock> {

    boolean isPresent(String store, Integer id) {
        Stock stock = get(store);
        return stock != null && stock.containsKey(id);
    }
    public static void main(String[] args) {

        Product p1 = new Product("Lait", "Machin", 2);
        Stock stock = new Stock();
        stock.put(1, p1);
        StoreStocks storeStocks = new StoreStocks();
        storeStocks.put("hello", stock);
        storeStocks.put("salut", stock);
        int id = 1;
        if (storeStocks.isPresent("salut", id))
            System.out.println("produit de id " +id + " présent");
        else
            System.out.println("produit de id " + id + " pas présent");
    }
}
