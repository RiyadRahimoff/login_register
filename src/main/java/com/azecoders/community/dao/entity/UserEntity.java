package com.azecoders.community.dao.entity;

import com.azecoders.community.model.enums.Profession;
import com.azecoders.community.model.enums.UserRole;
import com.azecoders.community.model.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "profile_photo")
    String profilePhoto;

    @Column(columnDefinition = "TEXT")
    String bio;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Enumerated(STRING)
    Profession profession;

    @Enumerated(STRING)
    @Column(name = "user_status")
    UserStatus userStatus;

    @Enumerated(STRING)
    @Column(name = "user_role")
    UserRole userRole;

    @Column(name = "is_enabled")
    boolean isEnabled;

    @Column(name = "profile_completed")
    boolean profileCompleted;

    @Column(name = "verification_token")
    String verificationToken;

    @Column(name = "is_verified")
    boolean isVerified;

    @Column(name = "is_premium")
    boolean isPremium;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

}
