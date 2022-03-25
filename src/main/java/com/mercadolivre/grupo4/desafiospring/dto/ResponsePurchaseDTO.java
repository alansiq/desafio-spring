package com.mercadolivre.grupo4.desafiospring.dto;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponsePurchaseDTO {
    Map<String,TicketDTO> ticket;

    public ResponsePurchaseDTO(TicketDTO ticket) {
        this.ticket = new HashMap<>();
        this.ticket.put("ticket",ticket);
    }

    @Override
    public String toString() {
        return "{" +
                "ticket:" + ticket +
                '}';
    }
}
