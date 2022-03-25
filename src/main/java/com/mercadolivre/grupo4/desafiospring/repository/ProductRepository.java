package com.mercadolivre.grupo4.desafiospring.repository;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.exception.ProductDoesNotExistException;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductRepository implements IProductRepository {
    private final String JSON_FILE = "src/main/resources/products.json";

    private boolean save(List<Product> productList) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(JSON_FILE), productList);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> getAll(){
        List<Product> result = new ArrayList<>();
        try{
            byte[] mapData = Files.readAllBytes(Paths.get(this.JSON_FILE));
            Product[] personagens = null;
            ObjectMapper objectMapper = new ObjectMapper();
            personagens = objectMapper.readValue(mapData, Product[].class);
            result = new ArrayList<>(Arrays.asList(personagens));
        } catch (JsonMappingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List<Product> orderByName(Integer order){
        List<Product> ordered = this.getAll().stream()
                .sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        if (order == 1) {
            Comparator<Product> comparator = Comparator.comparing(Product::getName);
            ordered.sort(comparator.reversed());
            return ordered;
        } else {
            orderByPrice(order);
        }
        return ordered;
    }

    public List<Product> orderByPrice(Integer order){
        List<Product> ordered = this.getAll().stream()
                .sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
        if (order == 3) {
            Comparator<Product> comparator = Comparator.comparing(Product::getPrice);
            ordered.sort(comparator.reversed());
            return ordered;
        }
        return ordered;
    }

    public List<Product> filterByName(String name) {
        return this.getAll().stream()
                .filter(product -> product.getName().equals(name)).collect(Collectors.toList());
    }

    public List<Product> filterByPrice(BigDecimal price) {
        return this.getAll().stream()
                .filter(product -> product.getPrice().equals(price)).collect(Collectors.toList());
    }

    public List<Product> filterByShipping(Boolean freeShipping) {
        return this.getAll().stream()
                .filter(product -> product.getFreeShipping().equals(freeShipping)).collect(Collectors.toList());
    }

    public List<Product> filterByCategory(String category) {
        return this.getAll().stream()
                .filter(product -> product.getCategory().equals(category)).collect(Collectors.toList());
    }

    public List<Product> filterByBrand(String brand) {
        return this.getAll().stream()
                .filter(product -> product.getBrand().equals(brand)).collect(Collectors.toList());
    }

    public List<Product> filterByPrestige(String prestige) {
        return this.getAll().stream()
                .filter(product -> product.getPrestige().equals(prestige)).collect(Collectors.toList());
    }

    @Override
    public boolean addList(List<Product> productsToAddList) {
        List<Product> productList = this.getAll();

        productList.addAll(productsToAddList);
        this.save(productList);

        return true;
    }

    @Override
    public boolean add(Product product) {
        try {
            List<Product> productList = this.getAll();
            productList.add(product);
            this.save(productList);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Product findById(Long id) {
        List<Product> allResult = this.getAll();
        Optional<Product> result = allResult.stream().filter(product -> product.getProductId().equals(id)).findFirst();
        if(result.isPresent()){
            return result.get();
        } else {
            throw new ProductDoesNotExistException("Algum produto informado não existe em nossos servidores!");
        }
    }
}
