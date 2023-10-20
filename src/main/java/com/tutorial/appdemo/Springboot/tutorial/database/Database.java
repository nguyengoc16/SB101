package com.tutorial.appdemo.Springboot.tutorial.database;

import com.tutorial.appdemo.Springboot.tutorial.models.Product;
import com.tutorial.appdemo.Springboot.tutorial.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration // class will contain Bean method
public class Database {
    //logger - replace for sysout
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean // is called imediately when app is called
    CommandLineRunner initDatabase(ProductRepository productRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product productA = new Product("Macbook pro",2020,2400.0,"");
                Product productB = new Product("Ipad pro",2020,450.0,"");
                logger.info("Insert data: " + productRepository.save(productA));
                logger.info("Insert data: " + productRepository.save(productB));
            }
        };
    }
}
