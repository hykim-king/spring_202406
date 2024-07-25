package com.pcwk.ehr.sync.domain;

import com.pcwk.ehr.cmn.DTO;

public class Sync extends DTO {

	private String name;

	public Sync() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Sync [name=" + name + ", toString()=" + super.toString() + "]";
	}


	
	
	
}
