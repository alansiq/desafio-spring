package com.mercadolivre.grupo4.desafiospring.controller;

import com.mercadolivre.grupo4.desafiospring.dto.*;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
  
    @GetMapping("/api/v1/products")
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        List<ProductDTO> convertedList = ProductDTO.convert(productService.listAllProducts());
        return ResponseEntity.status(HttpStatus.OK).body(convertedList);
    }

    @PostMapping("/api/v1/product")
    public ResponseEntity<List<ProductDTO>> insertProduct(@RequestBody List<Product> productList){
        boolean success = productService.save(productList);

        if (success) {
            return  ResponseEntity.ok().body(ProductDTO.convert(productList));
        }

        return ResponseEntity.badRequest().build();
    }


    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductDTO>> findByCategory(@RequestParam String categoryName){
        List<ProductDTO> result = productService.findByCategory(categoryName);
        return ResponseEntity.ok(result);
    }


    @PostMapping (path = "/compra")
    @ResponseBody
    public ResponseEntity<ResponsePurchaseDTO> findByCategory(@RequestBody Map<String,List<CompraItem>> purchaseRequest){
        PurchaseRequestDTO purchaseRequestDTO = new PurchaseRequestDTO(purchaseRequest);
        List<CompraItem> itemsList = purchaseRequestDTO.getCompraItem();
        System.out.println(itemsList);

        List<Product> produtosEmEstoque = productService.returnProductsInStock(itemsList);
        System.out.println(produtosEmEstoque);

        if(!produtosEmEstoque.isEmpty()){
            TicketDTO ticket = new TicketDTO();
            ticket.setArticles(produtosEmEstoque);
            Random generator = new Random();
            ticket.setID(generator.nextLong());
            BigDecimal preco = produtosEmEstoque.stream()
                    .map(Product::getPrice)
                    .reduce(BigDecimal.valueOf(0),BigDecimal::add);
            ticket.setTotal(Long.valueOf(preco.longValue()));

            ResponsePurchaseDTO response = new ResponsePurchaseDTO(ticket);
            System.out.println(response);
            return new ResponseEntity(response,HttpStatus.OK);
        }else{
            return  new ResponseEntity("produto nao cadastrado",HttpStatus.NOT_FOUND);
        }

    }

}
