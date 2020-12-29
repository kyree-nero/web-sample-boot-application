package com.example.app.web.security;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.audit.AuthExpressionRepository;

@Service
public class AuthorizationService {
	@Autowired private AuthExpressionRepository authExpressionRepository;
	
	@Transactional 
	public AuthExpression findExpression(String key) {
		return authExpressionRepository.getOne(key);
	}

	
	public List<AuthExpression> findAll() {
		return authExpressionRepository.findAll();
	}
}
