package com.example.csws.entity.instance;

import com.example.csws.entity.boundPolicy.InboundPolicy;
import com.example.csws.entity.lecture.Lecture;
import com.example.csws.entity.server.Server;
import com.example.csws.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int code;
    private String state;
    private Double storage;
    private String address;
    private int port;
    @Column(name = "keyname")
    private String keyName;
    private String os;
    private Timestamp created;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(targetEntity = Lecture.class)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
    @OneToMany(mappedBy = "instance", orphanRemoval = true)
    private List<InboundPolicy> inboundPolicy = new ArrayList<>();

    public InstanceDto toDto() {
        return InstanceDto.builder()
                .instanceId(id)
                .name(name)
                .code(code)
                .state(state)
                .storage(storage)
                .address(address)
                .port(port)
                .keyName(keyName)
                .username(user.getName())
                .os(os)
                .created(created)
                .userId(user.getId())
                .lectureId(lecture.getId())
                .build();
    }

    public void updateInstancePort(int port) {
        this.port = port;
    }

    public void updateStatus(String status) {
        this.state = status;
    }
}
