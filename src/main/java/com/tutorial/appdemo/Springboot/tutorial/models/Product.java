package com.tutorial.appdemo.Springboot.tutorial.models;

//POJO = Plain Object Java Object

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name="tblProduct")
public class Product {
    //this is PK
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // auto increment
    @SequenceGenerator( // quy tac
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 2 // increment by 1
    )
    @GeneratedValue( // su dung quy tac sequence o tren
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    @Column(nullable = false,unique = true, length = 300)
    private String productName;
    private int productYear;
    private  Double price;
    private String url;

    @Transient //calculated field = transient
    private int age;
    public  int getAge(){
        return Calendar.getInstance().get(Calendar.YEAR) - productYear;
    }

    public Product() {
    }

    public Product( String productName, int productYear, Double price, String url) {

        this.productName = productName;
        this.productYear = productYear;
        this.price = price;
        this.url = url;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", year=" + productYear +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return productYear == product.productYear
                && age == product.age && Objects.equals(id,product.id)
                && Objects.equals(productName,product.productName)
                && Objects.equals(price,product.price)
                && Objects.equals(url,product.url);
    }
}
