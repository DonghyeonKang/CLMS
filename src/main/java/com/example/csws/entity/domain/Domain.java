package com.example.csws.entity.domain;

import com.example.csws.entity.instance.Instance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor // builder 를 사용하기 위해 추가
@AllArgsConstructor // builder 를 사용하기 위해 추가
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne(targetEntity = Instance.class)  // 단방향 관계 형성은 한 쪽에서만 관계를 넣어주면 된다.
    @JoinColumn(name = "instance_id")   // instance 엔티티의 id 칼럼 값을 매핑한다는 의미
    private int instanceId;
}

