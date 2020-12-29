package com.example.app.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.web.security.AuthExpression;

public interface AuthExpressionRepository  extends JpaRepository<AuthExpression, String>{

}
