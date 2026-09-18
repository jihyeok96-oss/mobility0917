package com.oji.mobility.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterForm {

    @NotBlank
    @Size(min = 4, max = 50)
    private String userid;

    @NotBlank
    @Size(min = 8, max = 200)
    private String password;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Pattern(
            regexp = "^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$",
            message = "전화번호 형식은 01X-XXX(X)-XXXX 형식으로 입력해주세요.")
    private String tel1;

    @NotBlank
    @Email
    @Size(max = 120)
    private String email;
}
