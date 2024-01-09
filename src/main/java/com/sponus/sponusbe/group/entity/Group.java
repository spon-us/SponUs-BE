package com.sponus.sponusbe.group.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sponus.sponusbe.global.common.BaseEntity;
import com.sponus.sponusbe.group.enums.GroupType;
import com.sponus.sponusbe.group.enums.SubGroupType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "group")
public class Group extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private Long id;

	@Column(name = "group_name")
	@JsonProperty("name")
	@NonNull
	private String name;

	@Column(name = "group_email", unique = true)
	@JsonProperty("email")
	@NonNull
	private String email;

	@Column(name = "group_password")
	@JsonProperty("password")
	@NonNull
	private String password;

	@Column(name = "group_location")
	@JsonProperty("location")
	@NonNull
	private String location;

	@Column(name = "group_description")
	@JsonProperty("description")
	private String description;

	@Column(name = "group_image_url")
	@JsonProperty("image_url")
	private String imageUrl;

	@Column(name = "group_type")
	@Enumerated(EnumType.STRING)
	@JsonProperty("type")
	@NonNull
	private GroupType groupType;

	@Column(name = "subgroup_type")
	@Enumerated(EnumType.STRING)
	@JsonProperty("subtype")
	@NonNull
	private SubGroupType subGroupType;

	@Column(name = "manager_name")
	@JsonProperty("manager_name")
	@NonNull
	private String managerName;

	@Column(name = "manager_position")
	@JsonProperty("manager_position")
	@NonNull
	private String managerPosition;

	@Column(name = "manager_email")
	@JsonProperty("manager_email")
	@NonNull
	private String managerEmail;

	@Column(name = "manager_phone")
	@JsonProperty("manager_phone")
	@NonNull
	private String managerPhone;

	@Column(name = "manager_comment")
	@JsonProperty("manager_comment")
	@NonNull
	private String managerComment;

	@Column(name = "logined_at")
	@NonNull
	private LocalDateTime loginedAt;

	@Column(name = "group_status")
	@NonNull
	private String groupStatus;
}
