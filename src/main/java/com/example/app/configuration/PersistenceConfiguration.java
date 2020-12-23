package com.example.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.app.persistence.repositories")
public class PersistenceConfiguration {
//	@Bean
//	public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
//	    HibernateJpaSessionFactoryBean fact = new HibernateJpaSessionFactoryBean();
//	    fact.setEntityManagerFactory(emf);
//	    return fact;
//	}
}
