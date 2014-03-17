package com.common;

import com.opensymphony.xwork2.ActionSupport;

public class ServiceAction extends ActionSupport {

	public ServiceException se;

	public ServiceException getSe() {
		return se;
	}

	public void setSe(ServiceException se) {
		this.se = se;
	}
}
