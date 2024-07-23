package com.hb.system.ecommerce.shoes.entity;

import java.util.Date; 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "use_id")
    private User usuario;

    private String custFirstName;

    private String custLastName;

    private String custDni;

    private String custDepartment;

    private Date custBirthDate; 

    private String custCity;

    private String custProvince;

    private String custEmail;

    private String custPassword;

    private String custCellphone;

    private boolean custStatus;
}