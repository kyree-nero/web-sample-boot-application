package com.example.app.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.persistence.entities.AuthExpression;

public interface AuthExpressionRepository  extends JpaRepository<AuthExpression, String>{

}
