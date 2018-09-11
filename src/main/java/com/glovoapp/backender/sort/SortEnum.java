package com.glovoapp.backender.sort;

public enum SortEnum {

	DISTANCE("DISTANCE", new SortByLocation()),
	VIP("VIP", new SortByVip()),
	FOOD("FOOD", new SortByFood());

	private final String name;

	public Sort getSort() {
		return sort;
	}

	private final Sort sort;

	SortEnum(String name, Sort sort) {
		this.name = name;
		this.sort = sort;
	}
}
