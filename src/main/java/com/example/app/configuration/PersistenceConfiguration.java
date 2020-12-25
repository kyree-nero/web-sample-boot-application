package com.example.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.app.audit.AppAuditEventRepository;
import com.example.app.audit.AuthExpressionRepository;
import com.example.app.sample.SampleEntryRepository;

@Configuration
@EnableJpaRepositories(
		basePackageClasses = {
				AuthExpressionRepository.class,
				AppAuditEventRepository.class,
				SampleEntryRepository.class
		}
)
public class PersistenceConfiguration {
	
}
