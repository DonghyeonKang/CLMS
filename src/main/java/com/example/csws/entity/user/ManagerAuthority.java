package com.example.csws.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
public class ManagerAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String status;

    // entity 에 setter 를 구현하지 말고 메서드를 구현하라해서 update 하는 메서드 구현함
    // 승인
    public void acceptAuthorityStatus() {
        this.status = "accepted";
    }

    // 거절
    public void denyAuthorityStatus() {
        this.status = "denied";
    }
}
