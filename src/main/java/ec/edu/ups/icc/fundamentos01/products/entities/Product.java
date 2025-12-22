package ec.edu.ups.icc.fundamentos01.products.entities;

public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private String createdAt;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }

    // getters y setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
}
