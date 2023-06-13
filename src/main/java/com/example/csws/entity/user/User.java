package com.example.csws.entity.user;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.university.University;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Table(name = "Users")
@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String roles;
    private String phone;
    @ManyToOne(targetEntity = University.class) // university 엔티티와 관계를 설정할 것임을 명시
    @JoinColumn(name = "university_id") // university 엔티티의 id 값으로 칼럼을 join 함. join 한 칼럼명 설정도 할 수 있는데 기본인 university_id 로 사용
    private University university;
    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id")
    private Department department;

    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public void setManager() {
        this.roles = "ROLE_MANAGER";
    }
}
