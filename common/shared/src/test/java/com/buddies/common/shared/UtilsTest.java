package com.buddies.common.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
	private String name;
	private Integer age;
}

@Data
@AllArgsConstructor
class MortalEntity {
	private Integer age;
}

class UtilsTest {
	@Test
	void updateObjectWithNonNullValues() throws Exception {
		var srcObj = new Person("src", 10);
		var dest = new Person("dest", 12);
		var result = Utils.updateObject(srcObj, dest, Person.class);
		assertEquals(srcObj.getAge(), result.getAge());
		assertEquals(srcObj.getName(), result.getName());
	}
	
	@Test
	void updateObjectWithNullValues() throws Exception {
		var srcObj = new MortalEntity(null);
		var dest = new Person("dest", 12);
		var result = Utils.updateObject(srcObj, dest, Person.class);
		assertEquals(srcObj.getAge(), result.getAge());
		assertEquals(dest.getName(), result.getName());
	}

	@Test
	void updateObjectWithUnknownProperties() throws Exception {
		var srcObj = new Person();
		var dest = new MortalEntity(12);
		assertThrows(
				IllegalArgumentException.class, 
				() -> Utils.updateObject(srcObj, dest, MortalEntity.class));
	}
}
