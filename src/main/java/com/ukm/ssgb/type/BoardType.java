package com.ukm.ssgb.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardType {
	SOSANG("소상 톡톡"),
	SAUP("사업 톡톡")
	;

	private final String displayName;
}
