package com.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private GroupType type;

    private String typeName;

    private String refreshToken;

    private String proofImageUrl;

    @Builder
    public User(String email, String password, GroupType type, String typeName, String refreshToken, String proofImageUrl) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.typeName = typeName;
        this.refreshToken = refreshToken;
        this.proofImageUrl = proofImageUrl;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void invalidateRefreshToken() {
        this.refreshToken = null;
    }

    public void deleteProofImage() {
        this.proofImageUrl = null;
    }
}