package com.mercadolivre.grupo4.desafiospring.service;

import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.dto.ResponsePurchaseDTO;
import com.mercadolivre.grupo4.desafiospring.dto.TicketDTO;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.exception.ProductDoesNotExistException;
import com.mercadolivre.grupo4.desafiospring.exception.ProductQuantityDoesNotExistException;
import com.mercadolivre.grupo4.desafiospring.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean save(List<Product> productList) {
        return productRepository.addList(productList);
    }

    public List<ProductDTO> productsFilterBy(Optional<String> name, Optional<String> category,
                                             Optional<String> brand, Optional<BigDecimal> price,
                                             Optional<Boolean> freeShipping, Optional<String> prestige,
                                             Optional<Integer> order)
    {
        List<Product> listAfterFilters = productRepository.getAll();

        if (name.isPresent()) listAfterFilters = this.filterByName(name.get(), listAfterFilters);

        if (category.isPresent()) listAfterFilters = this.filterByCategory(category.get(), listAfterFilters);

        if (brand.isPresent()) listAfterFilters = this.filterByBrand(brand.get(), listAfterFilters);

        if (price.isPresent()) listAfterFilters = this.filterByPrice(price.get(), listAfterFilters);

        if (freeShipping.isPresent()) listAfterFilters = this.filterByShipping(freeShipping.get(), listAfterFilters);

        if (prestige.isPresent()) listAfterFilters =this.filterByPrestige(prestige.get(), listAfterFilters);

        return productsOrderBy(order, listAfterFilters);
    }

    public List<ProductDTO> productsOrderBy(Optional<Integer> order, List<Product> listAfterFilters) {
        List<Product> listAfterOrder = listAfterFilters;

        if (order.isPresent()) listAfterOrder = this.orderByName(order.get(), listAfterFilters);
      
        return ProductDTO.convert(listAfterOrder);
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
        ticket.setTotal(preco.longValue());
        return new ResponsePurchaseDTO(ticket);
    }

    public List<Product> returnProductsInStock(List<CompraItem> itemsList){
        List<Product> productsInStock = itemsList.stream().map(
                item -> productRepository.findById(item.getProductId())
        ).collect(Collectors.toList());
        if(!productsInStock.isEmpty()){
            return productsInStock;
        } else {
            throw new ProductDoesNotExistException("Algum produto informado não existe em nosso estoque!");
        }
    }

    public void verifyQuantityInStock(List<CompraItem> itemsList){
        List<Product> stock = productRepository.getAll();
        StringBuilder errors = new StringBuilder();
        for (Product x : stock) {
            for (CompraItem p : itemsList){
                if(x.getProductId().equals(p.getProductId())){
                    if(p.getQuantity() > x.getQuantity()){
                        errors.append("Quantidade solicitada do ").append(p.getName()).append(" não disponível. ").append("Total em Estoque: ").append(x.getQuantity()).append("\n");
                    }
                }
            }
        }
       if(!errors.toString().isEmpty()) throw new ProductQuantityDoesNotExistException(errors.toString());
    }

    // TODO: 26/03/22 FIX METHODS BELOW

    public List<Product> orderByName(Integer order, List<Product> listAfterFilters){
        List<Product> ordered = listAfterFilters.stream()
                .sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        if (order == 1) {
            Comparator<Product> comparator = Comparator.comparing(Product::getName);
            ordered.sort(comparator.reversed());
        } else {
            ordered = orderByPrice(order, ordered);
        }
        return ordered;
    }

    public List<Product> orderByPrice(Integer order, List<Product> listAfterFilters){
        List<Product> ordered = listAfterFilters.stream()
                .sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
        if (order == 3) {
            Comparator<Product> comparator = Comparator.comparing(Product::getPrice);
            ordered.sort(comparator.reversed());
            return ordered;
        }
        return ordered;
    }

    public List<Product> filterByName(String name, List<Product> listAfterFilters) {
        return listAfterFilters.stream()
                .filter(product -> product.getName().equals(name)).collect(Collectors.toList());
    }

    public List<Product> filterByPrice(BigDecimal price, List<Product> listAfterFilters) {
        return listAfterFilters.stream()
                .filter(product -> product.getPrice().equals(price)).collect(Collectors.toList());
    }

    public List<Product> filterByShipping(Boolean freeShipping, List<Product> listAfterFilters) {
        return listAfterFilters.stream()
                .filter(product -> product.getFreeShipping().equals(freeShipping)).collect(Collectors.toList());
    }

    public List<Product> filterByCategory(String category, List<Product> listAfterFilters) {
        return listAfterFilters.stream()
                .filter(product -> product.getCategory().equals(category)).collect(Collectors.toList());
    }

    public List<Product> filterByBrand(String brand, List<Product> listAfterFilters) {
        return listAfterFilters.stream()
                .filter(product -> product.getBrand().equals(brand)).collect(Collectors.toList());
    }

    public List<Product> filterByPrestige(String prestige, List<Product> listAfterFilters) {
        return listAfterFilters.stream()
                .filter(product -> product.getPrestige().equals(prestige)).collect(Collectors.toList());
    }
}
