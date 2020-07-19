package br.com.fooddelivery.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_user")
public class User {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateRegister;

    @ManyToMany
    @JoinTable(
            name = "tb_user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups = new HashSet<>();

    public boolean passwordMatches(String password) {
        return this.getPassword().equals(password);
    }

    public boolean passwordNotMatch(String password) {
        return !this.passwordMatches(password);
    }

    public boolean addGroup(Group group) {
        return this.getGroups().add(group);
    }

    public boolean removeGroup(Group group) {
        return this.getGroups().remove(group);
    }
}
