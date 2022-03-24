package com.mercadolivre.grupo4.desafiospring.repository;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepository {
    private static final String JSON_FILE = "products.json";


    //Todo: create method to insert entity in JSON file

    public List<Product> readAllProducts() {
        return this.jsonParaList(this.JSON_FILE);
    }

    public List<Product> jsonParaList(String arquivoJSON){
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




































































    public List<ProductDTO> save(List<ProductDTO> product) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            objectMapper.writeValue(new File(JSON_FILE),product);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
