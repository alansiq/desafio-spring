package com.mercadolivre.grupo4.desafiospring.service;


import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.dto.ResponsePurchaseDTO;
import com.mercadolivre.grupo4.desafiospring.dto.TicketDTO;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.exception.ProductDoesNotExistException;
import com.mercadolivre.grupo4.desafiospring.exception.ProductQuantityDoesNotExistException;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


import java.math.BigDecimal;

import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductDTO> orderByName(int order) {
        return productRepository.orderByName(order);
    }

    public List<ProductDTO> orderByPrice(int order) {
        return productRepository.orderByPrice(order);
    }

    public boolean save(List<Product> productList) {
        return productRepository.addList(productList);
    }


    public ResponsePurchaseDTO assemblePurchaseDTO(List<CompraItem> itemList){
        List<Product> produtosEmEstoque = returnProductsInStock(itemList);
        verifyQuantityInStock(itemList);
        TicketDTO ticket = new TicketDTO();
        ticket.setArticles(produtosEmEstoque);
        Random generator = new Random();
        ticket.setID(generator.nextLong());
        BigDecimal preco = produtosEmEstoque.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.valueOf(0),BigDecimal::add);
        ticket.setTotal(Long.valueOf(preco.longValue()));
        return new ResponsePurchaseDTO(ticket);

    }

    public List<ProductDTO> productsFilteredBy(Optional<String> name,
                                                 Optional<String> category,
                                                 Optional<String> brand,
                                                 Optional<BigDecimal> price,
                                                 Optional<Boolean> freeShipping,
                                                 Optional<String> prestige)
    {
        List<Product> resultList = productRepository.get();

        if (name.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getName() == null) return false;
                        return product.getName().equals(name.get());
                    }).collect(Collectors.toList());
        }

        if (category.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getCategory() == null) return false;
                        return product.getCategory().equals(category.get());
                    }).collect(Collectors.toList());
        }

        if (brand.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getBrand() == null) return false;
                        return product.getBrand().equals(brand.get());
                    }).collect(Collectors.toList());
        }

        if (price.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getPrice() == null) return false;
                        return product.getPrice().equals(price.get());
                    }).collect(Collectors.toList());
        }

        if (freeShipping.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getFreeShipping() == null) return false;
                        return product.getFreeShipping().equals(freeShipping.get());
                    }).collect(Collectors.toList());
        }

        if (prestige.isPresent()) {
            resultList =
                    resultList.stream().filter(product -> {
                        if (product.getPrestige() == null) return false;
                        return product.getPrestige().equals(prestige.get());
                    }).collect(Collectors.toList());
        }

        return ProductDTO.convert(resultList);
    }

    public List<Product> returnProductsInStock(List<CompraItem> itemsList){
        List<Product> productsInStock = itemsList.stream().map(
                item -> productRepository.findById(item.getProductId())
        ).collect(Collectors.toList());

        System.out.println("produtos");
        System.out.println(productsInStock);
        if(!productsInStock.isEmpty()){
            return productsInStock;
        } else {
            throw new ProductDoesNotExistException("Algum produto informado não existe em nossos servidores!");
        }

    }

    public void verifyQuantityInStock(List<CompraItem> itemsList){
        List<Product> stock = productRepository.get();

        //List<CompraItem> list = itemsList.stream().filter(
        //                itemList -> itemList.getQuantity() <
        //                        productRepository.findById(itemList.getProductId()).getQuantity())
        //        .collect(Collectors.toList());
        //System.out.println(list);
        StringBuilder errors = new StringBuilder();
        for (Product x : stock) {
            for (CompraItem p : itemsList){
                if(x.getProductId().equals(p.getProductId())){
                    if(p.getQuantity() > x.getQuantity()){
                        errors.append("Quantidade solicitada do "
                                + p.getName() + " não disponível. "
                                + "Total em Estoque: " + x.getQuantity() + "\n");
                    }
                }
            }
        }
        throw new ProductQuantityDoesNotExistException(errors.toString());
    }
}
