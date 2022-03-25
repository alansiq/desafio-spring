package com.mercadolivre.grupo4.desafiospring.dto;

import com.mercadolivre.grupo4.desafiospring.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class TicketDTO {

    private Map<String,Object> ticket = new HashMap<>();

    public TicketDTO(Long id, List<Product> list, Long total) {
        ticket = new HashMap<>();
        ticket.put("id",id);
        ticket.put("articles", list);
        ticket.put("total",total);
    }

    public void setID (Long id){
        ticket.put("id",id);
    }
    public void setArticles(List<Product> list){
        ticket.put("articles", list);
    }
    public void setTotal(Long total) {
        ticket.put("total", Long.valueOf(total) );
    }

    @Override
    public String toString() {
        return ""+ ticket +"";
    }
}
