package com.application.gentlegourmet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "pw", length = 100, nullable = false)
    private String password;

    private boolean enabled = true; //set as true to always allow Spring Security Authentication

    ///////////////////////////////////////////////////////////////////////////

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cart> carts;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Purchase> purchases;

    ///////////////////////////////////////////////////////////////////////////

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
