package br.com.fooddelivery.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_photo_product")
public class ProductPhoto {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "product_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;

    @NotBlank
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank
    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size")
    private Long size;
}