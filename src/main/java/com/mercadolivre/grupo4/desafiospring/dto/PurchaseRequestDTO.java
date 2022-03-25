package com.mercadolivre.grupo4.desafiospring.dto;

import com.mercadolivre.grupo4.desafiospring.entity.CompraItem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseRequestDTO implements Serializable {
    private Map<String,List<CompraItem>> articlesPurchaseRequest;

    public PurchaseRequestDTO(Map<String,List<CompraItem>> purchaseRequest) {
        this.articlesPurchaseRequest = new HashMap<>();
        this.articlesPurchaseRequest.put("articlesPurchaseRequest", purchaseRequest.get("articlesPurchaseRequest"));
    }

    public List<CompraItem> getCompraItem(){
        return articlesPurchaseRequest.get("articlesPurchaseRequest");
    }

    @Override
    public String toString() {
        return "PurchaseRequestDTO{" +
                "articlesPurchaseRequest=" + articlesPurchaseRequest +
                '}';
    }
}
