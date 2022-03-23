package com.mercadolivre.grupo4.desafiospring.repository;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {
    private static final String JSON_FILE = "products.json";

    //Todo: create method to insert entity in JSON file

    private List<Product> jsonParaList(String arquivoJSON){
        List<Product> result = new ArrayList<>();
        try{
            byte[] mapData = Files.readAllBytes(Paths.get(arquivoJSON));
            Product[] personagens = null;
            ObjectMapper objectMapper = new ObjectMapper();
            personagens = objectMapper.readValue(mapData, Product[].class);
            result = Arrays.asList(personagens);
        } catch (JsonMappingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
























    public List<ProductDTO> findByCategory(String category) {
        List<Product> allResult = jsonParaList(JSON_FILE);
        List<Product> filterResuts = allResult.stream().filter(product -> product.getCategory().equals(category)).collect(Collectors.toList());
        List<ProductDTO> result = filterResuts.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
        return result;
    }
}
