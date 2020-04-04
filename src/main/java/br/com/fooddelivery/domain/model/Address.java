package br.com.fooddelivery.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class Address {
    @Column(name = "address_zip_code")
    private String zipCode;

    @Column(name = "address_public_place")
    private String publicPlace;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_neighborhood")
    private String neighborhood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
}
