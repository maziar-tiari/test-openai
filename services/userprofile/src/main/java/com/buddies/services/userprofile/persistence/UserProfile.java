package com.buddies.services.userprofile.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import org.hibernate.annotations.Type;

import com.buddies.services.userprofile.types.Gender;
import com.buddies.services.userprofile.types.RelationshipStatus;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@Entity(name = "UserProfile")
@Table(name = "user_profile")
@NoArgsConstructor
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_gen")
	@SequenceGenerator(name = "user_profile_gen", sequenceName = "user_profile_seq")
	private Long id;

	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;

	@Column(name = "username", length = ColumnLength.USERNAME, unique = true)
	private String username;

	@Column(name = "first_name", length = ColumnLength.FIRST_NAME)
	private String firstName;

	@Column(name = "surname", length = ColumnLength.SURNAME)
	private String surname;

	@Column(name = "avatar_image_id")
	private String avatarImageId;

	@Type(ListArrayType.class)
	@Column(name = "activity_ids", columnDefinition = "bigint[]")
	private List<Long> activityIds;

	@Type(ListArrayType.class)
	@Column(name = "applied_activity_ids", columnDefinition = "bigint[]")
	private List<Long> appliedActivityIds;

	@Type(ListArrayType.class)
	@Column(name = "member_activity_ids", columnDefinition = "bigint[]")
	private List<Long> memberActivityIds;

	@Type(ListArrayType.class)
	@Column(name = "group_ids", columnDefinition = "bigint[]")
	private List<Long> groupIds;

	@Type(ListArrayType.class)
	@Column(name = "job_ids", columnDefinition = "bigint[]")
	private List<Long> jobIds;

	@Type(ListArrayType.class)
	@Column(name = "languages", columnDefinition = "text[]")
	private List<String> languages;

	// TODO: we need a format that allows filtering and clustering
	@Column(name = "location")
	private String location;

	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;

	@Column(name = "gender")
	private Gender gender;

	@Column(name = "info", length = ColumnLength.INFO)
	private String info;

	@Column(name = "relationship_status")
	private RelationshipStatus relationshipStatus;

	public void addActivityId(Long id) {
		this.activityIds.add(id);
	}

	public void addAppliedActivityId(Long id) {
		this.appliedActivityIds.add(id);
	}

	public void addMemberActivityId(Long id) {
		this.memberActivityIds.add(id);
	}

	public void addGroupId(Long id) {
		this.groupIds.add(id);
	}

	public void addJobId(Long id) {
		this.jobIds.add(id);
	}

	public void addLanguage(String language) {
		this.languages.add(language);
	}
}