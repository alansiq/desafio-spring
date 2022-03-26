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
import java.math.BigDecimal;
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

        if (name.isPresent()) listAfterFilters = productRepository.filterByName(name.get());

        if (category.isPresent()) listAfterFilters = productRepository.filterByCategory(category.get());

        if (brand.isPresent()) listAfterFilters = productRepository.filterByBrand(brand.get());

        if (price.isPresent()) listAfterFilters = productRepository.filterByPrice(price.get());

        if (freeShipping.isPresent()) listAfterFilters = productRepository.filterByShipping(freeShipping.get());

        if (prestige.isPresent()) listAfterFilters =productRepository.filterByPrestige(prestige.get());

        return productsOrderBy(order, listAfterFilters);
    }

    public List<ProductDTO> productsOrderBy(Optional<Integer> order, List<Product> listAfterFilters) {
        List<Product> listAfterOrder = listAfterFilters;

        if (order.isPresent()) listAfterOrder = productRepository.orderByName(order.get());
      
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
}
