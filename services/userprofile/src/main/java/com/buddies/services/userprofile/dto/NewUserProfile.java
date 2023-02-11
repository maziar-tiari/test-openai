package com.buddies.services.userprofile.dto;

import java.time.LocalDate;
import java.util.List;

import com.buddies.services.userprofile.persistence.ColumnLength;
import com.buddies.services.userprofile.types.Gender;
import com.buddies.services.userprofile.types.RelationshipStatus;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserProfile {
	@Size(min = 2, max = ColumnLength.FIRST_NAME, message = "firstname should have min length of 2 and max 100")
	private String firstName;

	@Size(min = 2, max = ColumnLength.SURNAME, message = "firstname should have min length of 2 and max 100")
	private String surname;

	private List<String> languages;

	private String location;

	private LocalDate birthDate;

	private Gender gender;

	@Size(min = 5, max = ColumnLength.INFO, message = "info should have max length of 500")
	private String info;

	private RelationshipStatus relationshipStatus;
}
