package com.example.csws.config.jwt;

import com.example.csws.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String token;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "User_id")
    private User user;

    public void updateToken(String token) {
        this.token = token;
    }
}
