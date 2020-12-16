package com.example.app.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.app.domain.validator.BadContentConstraint;

public class Sample{
	private Long id;
	@NotBlank
	@Pattern(
			regexp = "^[A-Za-z0-9\\s]+$", 
			message= "characters must be alphanumeric or space"
	)
	@Size(
			min=1, max=100, 
			message="characters must be less than 100 characters long"
	)
	@BadContentConstraint
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
