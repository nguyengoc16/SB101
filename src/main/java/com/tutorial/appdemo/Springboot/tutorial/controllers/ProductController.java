package com.tutorial.appdemo.Springboot.tutorial.controllers;

import com.tutorial.appdemo.Springboot.tutorial.models.Product;
import com.tutorial.appdemo.Springboot.tutorial.models.ResponseObject;
import com.tutorial.appdemo.Springboot.tutorial.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // mark as the controller
@RequestMapping(path="/api/v1/Products") //map the url
public class ProductController {
    //DI = Dependency Injection
    @Autowired //singleton
    private ProductRepository repository;
    @GetMapping("/getAllProducts")
    // http://localhost:8080/api/v1/Products/getAllProducts
    List<Product> getAllProducts(){

        return repository.findAll();
    }

    //Get detail product
    @GetMapping("/{id}")
    // Return an object with: data,message,status
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Query successful",foundProduct)):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Query unsuccessful",foundProduct));
    }

    //Insert product new Product
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){
        //2 products must not have same name
        List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
        if(foundProducts.size() > 0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed","Product name already exists",null)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Insert Product succesfully",repository.save(newProduct))
            );
        }

    }
    //update, upsert = update if found, otherwise insert
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id){
        Product updatedProduct = repository.findById(id).
                map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setProductYear(newProduct.getProductYear());
                    product.setPrice(newProduct.getPrice());
                    product.setUrl(newProduct.getUrl());
                    return repository.save(product);
                }).orElseGet(()->{
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Product successfully", updatedProduct)
        );
    }
    // delete a product
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id){
        boolean exists = repository.existsById(id);
        if(exists){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete successfully", null)
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Not found product", null)
            );
        }
    }
}
