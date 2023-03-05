package com.dev.accessrefreshtoken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.accessrefreshtoken.model.User;
import com.dev.accessrefreshtoken.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/me")
	public ResponseEntity<?> me(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(user);
	}

	@GetMapping("/{id}")
	@PreAuthorize("#user.id == #id")
	public ResponseEntity<?> me(@AuthenticationPrincipal User user, @PathVariable String id) {
		return ResponseEntity.ok(userRepository.findById(id));
	}

}
