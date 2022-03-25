package com.mercadolivre.grupo4.desafiospring.controller;

import com.mercadolivre.grupo4.desafiospring.dto.*;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam(required = false) Optional<Integer> name ,
                                                                    @RequestParam(required = false) Optional<String> category,
                                                                    @RequestParam(required = false) Optional<String> brand,
                                                                    @RequestParam(required = false) Optional<Integer> price,
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
