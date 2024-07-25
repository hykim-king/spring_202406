package com.pcwk.ehr.naver.domain;

import java.util.ArrayList;
import java.util.List;

public class Channel {
	
	private List<Item> items=new ArrayList<Item>();

	public Channel() {
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
	
}
