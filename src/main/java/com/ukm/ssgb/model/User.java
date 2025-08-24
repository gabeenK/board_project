package com.ukm.ssgb.model;

import com.ukm.ssgb.type.LoginType;
import com.ukm.ssgb.type.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.ukm.ssgb.constant.ServiceConstants.DELETED_USER_NICKNAME;
import static com.ukm.ssgb.type.RoleType.*;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column
    private String email;

    @Column
    @Setter
    private String password;

    @Column
    @Setter
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleType role;

    @Column
    private LocalDateTime agreedServiceAt;

    @Setter
    @Column
    private Long businessFileId;

    @Column
    @Setter
    private Long profileImageId;

    @Column
    @Setter
    private Long regionId;

    @Column
    @Setter
    private Long businessTypeId;

    @Setter
    @Column
    private Boolean approved;

    @Column
    private Boolean deleted;

    @Enumerated(EnumType.STRING)
    @Column
    private LoginType loginType;

    @Column
    private LocalDateTime lastLoginAt;

    public void deleteProfileImage() {
        this.profileImageId = null;
    }

    public static User registerUser(String email, String password, String nickname) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(ROLE_PENDING_USER)
                .agreedServiceAt(LocalDateTime.now())
                .approved(false)
                .deleted(false)
                .loginType(LoginType.EMAIL)
                .build();
    }

    public static User registerAdmin(String email, String password, String nickname) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(ROLE_ADMIN)
                .agreedServiceAt(LocalDateTime.now())
                .approved(false)
                .deleted(false)
                .loginType(LoginType.EMAIL)
                .build();
    }

    @Builder
    private User(String email, String password, String nickname, RoleType role, LocalDateTime agreedServiceAt,
                 Long businessFileId, Long profileImageId, Long regionId, Long businessTypeId, Boolean approved,
                 Boolean deleted, LoginType loginType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.agreedServiceAt = agreedServiceAt;
        this.businessFileId = businessFileId;
        this.profileImageId = profileImageId;
        this.regionId = regionId;
        this.businessTypeId = businessTypeId;
        this.approved = approved;
        this.deleted = deleted;
        this.loginType = loginType;
    }

    public boolean existsBusinessFile() {
        return businessFileId != null;
    }

    public void delete() {
        this.deleted = true;
        this.nickname = DELETED_USER_NICKNAME;
    }

    public void approve() {
        this.approved = true;
        if (this.role == ROLE_PENDING_USER) {
            this.role = ROLE_USER;
        }
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }
}
