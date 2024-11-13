package com.brix.Seller_Sync.store;

import java.util.ArrayList;
import java.util.List;

import com.brix.Seller_Sync.brand.Brand;
import com.brix.Seller_Sync.client.Client;
import com.brix.Seller_Sync.common.BaseEntity;
import com.brix.Seller_Sync.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store")
public class Store extends BaseEntity {

    @Column()
    private String name;

    @Column()
    private Boolean active = true;

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Client> clients = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<Brand> brands = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
