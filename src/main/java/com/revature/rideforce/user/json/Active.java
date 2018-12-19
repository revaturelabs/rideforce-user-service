package com.revature.rideforce.user.json;

public enum Active {

	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	DISABLED("DISABLED");
	
	private String code;
	
	Active(String code){
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	}
