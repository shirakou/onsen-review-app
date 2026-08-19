package com.example.onsenreviewapp.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewForm {
	
	@NotBlank
	@Size(max = 100)
	private String title;
	
	@NotNull
	@Min(1)
	@Max(5)
	private Short rating;
	
	@NotBlank
	private String comment;
}
