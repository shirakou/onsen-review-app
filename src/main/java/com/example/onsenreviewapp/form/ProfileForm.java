package com.example.onsenreviewapp.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileForm {

	@NotBlank(message = "ユーザー名を入力してください")
	@Size(max = 50, message = "ユーザー名は50文字以内で入力してください")
	private String username;
}
