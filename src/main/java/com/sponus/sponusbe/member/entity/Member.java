package com.sponus.sponusbe.member.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sponus.sponusbe.global.common.BaseEntity;
import com.sponus.sponusbe.member.enums.Role;

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

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(name = "member_email", unique = true)
	@JsonProperty("email")
	private String email;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	@JsonProperty("role")
	private Role role;

	@Column(name = "member_name")
	@JsonProperty("name")
	private String name;

	@Column(name = "member_password")
	@JsonProperty("password")
	private String password;
}
