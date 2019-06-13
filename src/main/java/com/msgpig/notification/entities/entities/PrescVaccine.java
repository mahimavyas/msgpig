package com.msgpig.notification.entities.entities;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescVaccine implements Serializable {
    private static final long serialVersionUID = 1L;
    private String comments;
    private Long date;
    
    private String vaccineId;

    private String vaccineName;
    
    private String vaccineDesc;
    
    private String contents;
    
    private String packing;
    
    private Double mrp;
    
    private Double price;
    
    private Boolean isVerified;
    private List<String> diseaseIds;
    private List<String> symptomsIds;
    private List<String> specializationIds;

}
