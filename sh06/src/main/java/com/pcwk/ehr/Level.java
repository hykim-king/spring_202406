package com.pcwk.ehr;

/**
 * 사용자 등급:BASIC, SILVER, GOLD
 * 
 * @author acorn
 *
 */
public enum Level {

	BASIC(1), SILVER(2), GOLD(3);

	private final int value;

	Level(int value) {
		this.value = value;
	}

	public int intValue() {
		return value;
	}

	// 값으로 부터 Level 오브젝트 Retrurn
	public static Level valueOf(int value) {

		switch (value) {
		case 1:
			return BASIC;
		case 2:
			return SILVER;
		case 3:
			return GOLD;
		default:
			throw new AssertionError("Unknown value:" + value);
		}
	}

}
