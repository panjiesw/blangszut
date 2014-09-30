package com.panjiesw.blangszut.persistence.entities.enums;

public enum UserStatus {
	DISABLED(0), ACTIVE(1), LOCKED(-1);

	private Integer status;

	UserStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public static UserStatus getType(Integer status) {
		if (status == null)
			return null;
		for (UserStatus stat : UserStatus.values()) {
			if (status.equals(stat.getStatus()))
				return stat;
		}
		throw new IllegalArgumentException("No matching type for status " + status);
	}
}
