package com.dev.accessrefreshtoken.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

	private String userId;

	private String accessToken;

	private String refreshToken;

}
