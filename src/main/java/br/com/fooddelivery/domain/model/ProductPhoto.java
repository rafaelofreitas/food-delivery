package br.com.fooddelivery.domain.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_product_photo")
public class ProductPhoto {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "product_product_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size")
    private Long size;

    public Integer getRestaurantId() {
        if (this.getProduct() == null) {
            return null;
        }

        return this.getProduct().getRestaurant().getId();
    }

    public Integer getProductId() {
        if (this.getProduct() == null) {
            return null;
        }

        return this.getProduct().getId();
    }
}