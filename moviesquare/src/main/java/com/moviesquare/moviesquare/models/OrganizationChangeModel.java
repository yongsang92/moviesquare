package com.moviesquare.moviesquare.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrganizationChangeModel{
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;


    public  OrganizationChangeModel(String type, String action, String organizationId, String correlationId) {
        super();
        this.type   = type;
        this.action = action;
        this.organizationId = organizationId;
        this.correlationId = correlationId;
    }


}