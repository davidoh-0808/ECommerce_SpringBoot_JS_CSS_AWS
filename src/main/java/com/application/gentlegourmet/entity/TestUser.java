package com.application.gentlegourmet.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "test_user")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TestUser {

    @Id
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "pw")
    private String pw;

    public TestUser(String userId, String pw) {
        this.userId = userId;
        this.pw = pw;
    }


}
