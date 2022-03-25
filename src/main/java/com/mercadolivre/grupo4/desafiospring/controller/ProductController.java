package com.mercadolivre.grupo4.desafiospring.controller;

import com.mercadolivre.grupo4.desafiospring.dto.*;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Optional;

@Validated
@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("api/v1/products")
    public ResponseEntity<List<ProductDTO>> listAllProductsFiltered(@RequestParam(required = false) Optional<String> name ,
                                                                    @RequestParam(required = false) Optional<String> category,
                                                                    @RequestParam(required = false) Optional<String> brand,
                                                                    @RequestParam(required = false) Optional<BigDecimal> price,
                                                                    @RequestParam(required = false) Optional<Boolean> freeShipping,
                                                                    @RequestParam(required = false) Optional<String> prestige
                                                                    )
    {

        List<ProductDTO> result = productService.productsFilteredBy(name, category, brand, price, freeShipping, prestige);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/api/v1/product")
    public ResponseEntity<List<ProductDTO>> insertProduct(@Valid @RequestBody List<Product> productList){
        boolean success = productService.save(productList);
        if (success) {
            return  ResponseEntity.status(HttpStatus.CREATED).body(ProductDTO.convert(productList));
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductDTO>> findByCategory(@RequestParam String categoryName) {
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

    @GetMapping("/api/v1/articles")
    public ResponseEntity<List<ProductDTO>> orderByName(@RequestParam(value = "order", defaultValue = "0") Integer order) {
        List<ProductDTO> result = productService.orderByName(order);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/v1/articles2")
    public ResponseEntity<List<ProductDTO>> orderByPrice(@RequestParam(value = "order", defaultValue = "2") int order) {
            List<ProductDTO> result = productService.orderByPrice(order);
        return ResponseEntity.ok(result);
    }
}
