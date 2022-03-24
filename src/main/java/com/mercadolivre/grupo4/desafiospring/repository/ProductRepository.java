package com.mercadolivre.grupo4.desafiospring.repository;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
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
    public List<Product> get(){
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

    public List<ProductDTO> findByCategory(String category) {
        List<Product> allResult = this.get();
        List<Product> filterResuts = allResult.stream().filter(product -> product.getCategory().equals(category)).collect(Collectors.toList());
        List<ProductDTO> result = filterResuts.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
        return result;
    }

    public List<ProductDTO> orderByName(int order){
        List<Product> allResult = this.get();
        List<Product> ordered = allResult.stream().sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        if (order == 1) {
            Comparator<Product> comparator = Comparator.comparing(Product::getName);
            ordered.sort(comparator.reversed());
            return ordered.stream().map(ProductDTO::new).collect(Collectors.toList());
        }
        return ordered.stream().map(ProductDTO::new).collect(Collectors.toList());
    }
//.sorted((a, b) -> new BigDecimal(b).compareTo(new BigDecimal(a)))
//            .collect(Collectors.joining(", ")));
//    (Product product1, Product product2)-> (int)(product1.getPrice()-product2.getPrice())
    public List<ProductDTO> orderByPrice(int order){
        List<Product> allResult = this.get();
        List<Product> ordered = allResult.stream().sorted((a, b) -> new BigDecimal(b.getPrice()).compareTo(new BigDecimal(a.getPrice())))
                .collect(Collectors.toList());
        if (order == 3) {
            Comparator<Product> comparator = Comparator.comparing(Product::getPrice);
            ordered.sort(comparator.reversed());
            return ordered.stream().map(ProductDTO::new).collect(Collectors.toList());
        }
        return ordered.stream().map(ProductDTO::new).collect(Collectors.toList());
    }



    @Override
    public boolean addList(List<Product> productsToAddList) {
        List<Product> productList = this.get();

        productList.addAll(productsToAddList);
        this.save(productList);

        return true;
    }

    @Override
    public boolean add(Product product) {
        try {
            List<Product> productList = this.get();
            productList.add(product);
            this.save(productList);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
