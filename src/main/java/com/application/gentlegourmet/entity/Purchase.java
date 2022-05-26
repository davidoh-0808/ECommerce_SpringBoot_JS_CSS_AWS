package com.application.gentlegourmet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private Date created;

    ///////////////////////////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PurchaseDetail> purchaseDetails;

    @OneToOne
    private ProductReview productReview;

    ///////////////////////////////////////////////////////////////////////////

}
