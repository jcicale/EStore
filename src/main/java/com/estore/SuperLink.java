package com.estore;

import org.springframework.hateoas.Link;

public class SuperLink extends Link {

	private static final long serialVersionUID = 1L;
	private String method;

	  public SuperLink(Link link, String method) {
	    super(link.getHref(), link.getRel());
	    this.method = method;
	  }

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	  
	  
}
