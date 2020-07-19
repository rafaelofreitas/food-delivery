package br.com.fooddelivery.domain.model;

import br.com.fooddelivery.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_city")
public class City {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Valid
    @ConvertGroup(to = Groups.StateId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}