package com.oji.mobility.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String userid;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tel;

    @Email(message = "이메일이 형식에 맞지 않습니다")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Range(
            min = 14,
            max = 150,
            message = "가입 가능 연령이 아닙니다."
    )
    private Integer age;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Board> boards = new ArrayList<>();
}
