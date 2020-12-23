package com.example.app;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.app.audit.AuditingService;

@ExtendWith(SpringExtension.class)

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@TestPropertySource("/application-test.properties")
@Sql({
	"/META-INF/data/h2/drops.sql", 
	"/META-INF/data/h2/tables.sql", 
	"/META-INF/data/h2/inserts.sql"
})
public abstract class AbstractIT {
	@Autowired AuditingService auditingService;
	
	protected Instant startInstant = Instant.ofEpochMilli(new Date().getTime());
	
	
}
