//package org.yapp.domain.user.domain;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Table(name = "USER")
//@Entity
//@Getter
//@NoArgsConstructor
//public class User {
//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "OAUTH_ID",nullable = false)
//    private String oauthId;
//
//    @Builder
//    public User(String oauthId) {
//        this.oauthId = oauthId;
//    }
//}
