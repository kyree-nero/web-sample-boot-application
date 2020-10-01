package com.example.app;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}
