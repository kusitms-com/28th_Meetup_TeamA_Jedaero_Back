package com.backend.domain.user.entity;

import com.backend.common.domain.BaseEntity;
import com.backend.domain.store.entity.Pick;
import com.backend.domain.store.entity.Store;
import com.backend.domain.university.entity.University;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "user")
    private List<Pick> picks = new ArrayList<>();

    @Builder
    public User(String email, String password, GroupType type, String typeName, String refreshToken, String proofImageUrl, University university) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.typeName = typeName;
        this.refreshToken = refreshToken;
        this.proofImageUrl = proofImageUrl;
        this.university = university;
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

    public Pick createPick(Store store) {
        Pick pick = Pick.builder()
                .user(this)
                .store(store)
                .build();
        picks.add(pick);
        store.add(pick);
        return pick;
    }

    public void delete(Pick pick) {
        pick.delete();
        picks.remove(pick);
    }

}