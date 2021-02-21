CREATE DATABASE sample;

USE sample;

CREATE TABLE sample.SAMPLE (
	 id BIGINT NOT NULL AUTO_INCREMENT, 
	 content VARCHAR(100) NOT NULL,
	 version INT NOT NULL, 
	 PRIMARY KEY (id)
); 

CREATE TABLE sample.AUTH_EXPR(
	resource 		VARCHAR(200) NOT NULL,
	policy_expr 	VARCHAR(500) NOT NULL
);


CREATE TABLE AUDIT_EVENT(
	id 				BIGINT NOT NULL AUTO_INCREMENT, 
	ts_num			BIGINT NOT NULL,
	user			VARCHAR(100) not null,
	type			VARCHAR(100) not null, 
	PRIMARY KEY (id)
);


CREATE TABLE AUDIT_EVENT_DATA(
	id 				BIGINT NOT NULL AUTO_INCREMENT, 
	event_id		BIGINT NOT NULL,
	audit_key		VARCHAR(100) not null,
	audit_value		VARCHAR(500) not null, 
	PRIMARY KEY (id)	
);

-- Service data
INSERT INTO SAMPLE (ID, CONTENT, VERSION) VALUES (5000, 'hi im going to be deleted', 0);
INSERT INTO SAMPLE (ID, CONTENT, VERSION) VALUES (0, 'hi im a sample', 0);

-- Authentication
INSERT INTO AUTH_EXPR (RESOURCE, POLICY_EXPR) VALUES ('/', 'true');
INSERT INTO AUTH_EXPR (RESOURCE, POLICY_EXPR) VALUES ('/sample.*', 'hasRole(''ROLE_USERS'')');
INSERT INTO AUTH_EXPR (RESOURCE, POLICY_EXPR) VALUES ('/read', 'hasRole(''ROLE_USERS'')');
INSERT INTO AUTH_EXPR (RESOURCE, POLICY_EXPR) VALUES ('/logout', 'true');
INSERT INTO AUTH_EXPR (RESOURCE, POLICY_EXPR) VALUES ('.*html', 'true');

COMMIT;