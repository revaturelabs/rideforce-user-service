package com.revature.rideforce.user.beans;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revature.rideforce.user.json.JsonLink;
import com.revature.rideforce.user.json.OfficeLinkResolver;

public class RegistrationToken {
	@Valid
	@NotNull
	@JsonLink(OfficeLinkResolver.class)
	private Office office;
	@Future
	private Date batchEndDate;

	public RegistrationToken() {}

	public RegistrationToken(@Valid @NotNull Office office, @Future Date batchEndDate) {
		this.office = office;
		this.batchEndDate = batchEndDate;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Date getBatchEndDate() {
		return batchEndDate;
	}

	public void setBatchEndDate(Date batchEndDate) {
		this.batchEndDate = batchEndDate;
	}
}
