package com.mercadolivre.grupo4.desafiospring.controller;
import com.mercadolivre.grupo4.desafiospring.dto.ProductDTO;
import com.mercadolivre.grupo4.desafiospring.dto.ResponsePurchaseDTO;
import com.mercadolivre.grupo4.desafiospring.dto.PurchaseRequestDTO;
import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;
import com.mercadolivre.grupo4.desafiospring.entity.Product;
import com.mercadolivre.grupo4.desafiospring.exception.ProductDoesNotExistException;
import com.mercadolivre.grupo4.desafiospring.exception.ProductQuantityDoesNotExistException;
import com.mercadolivre.grupo4.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam(required = false) Optional<String> name ,
                                                        @RequestParam(required = false) Optional<String> category,
                                                        @RequestParam(required = false) Optional<String> brand,
                                                        @RequestParam(required = false) Optional<BigDecimal> price,
                                                        @RequestParam(required = false) Optional<Boolean> freeShipping,
                                                        @RequestParam(required = false) Optional<String> prestige,
                                                        @RequestParam(required = false) Optional<Integer> order
                                                        )

    {
        List<ProductDTO> result = productService
                .productsFilterBy(name, category, brand, price, freeShipping, prestige, order);

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

    @PostMapping (path = "/api/v1/purchase-request")
    @ResponseBody
    public ResponseEntity<ResponsePurchaseDTO> purchase(@RequestBody Map<String,List<CompraItem>> purchaseRequest){
        PurchaseRequestDTO purchaseRequestDTO = new PurchaseRequestDTO(purchaseRequest);
        List<CompraItem> itemsList = purchaseRequestDTO.getCompraItem();

        try {
            ResponsePurchaseDTO response = productService.assemblePurchaseDTO(itemsList);
            return new ResponseEntity(response,HttpStatus.OK);
        }
        catch (ProductDoesNotExistException E){
            return  new ResponseEntity(E.getMessage(),HttpStatus.NOT_FOUND);
        } catch (ProductQuantityDoesNotExistException E) {
            return new ResponseEntity(E.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}