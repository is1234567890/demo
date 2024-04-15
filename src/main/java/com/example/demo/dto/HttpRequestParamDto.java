package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レコード取得DTO.<br/>
 * 
 * @author iwamura.sou
 * @since 2024/04/14
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class HttpRequestParamDto {
	/** 社員ID */
	@NotBlank
	@Size(min = 8, max = 8)
	@Pattern(regexp = "^[0-9]{8}$")
	private String employeeId;
}
