package com.smd.webapi.handler;

public class MandatoryInfoException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public MandatoryInfoException(String mandatory) {
		super("%s is mandatory", mandatory);
	}
	

}
