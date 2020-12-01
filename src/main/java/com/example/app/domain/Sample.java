package com.example.app.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Sample{
	private Long id;
	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9\\s]+$", message= "characters must be alphanumeric or space")
	private String content;
	private Long version;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	
}
