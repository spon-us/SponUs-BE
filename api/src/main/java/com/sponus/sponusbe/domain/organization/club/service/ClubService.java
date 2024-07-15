package com.sponus.sponusbe.domain.organization.club.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.club.Club;
import com.sponus.coredomain.domain.organization.club.ClubType;
import com.sponus.coredomain.domain.organization.repository.ClubRepository;
import com.sponus.coredomain.domain.organization.repository.ClubTypeRepository;
import com.sponus.sponusbe.domain.organization.club.dto.ClubGetResponse;
import com.sponus.sponusbe.domain.organization.club.dto.ClubUpdateRequest;
import com.sponus.sponusbe.domain.organization.exception.ClubErrorCode;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {
	private final ClubRepository clubRepository;
	private final ClubTypeRepository clubTypeRepository;

	public ClubGetResponse getClub(Long clubId) {
		final Club club = findClubById(clubId);
		return ClubGetResponse.of(club);
	}

	public void updateClub(Long clubId, ClubUpdateRequest request) {
		final Club club = findClubById(clubId);
		club.updateInfo(
			request.name(),
			request.description(),
			request.imageUrl(),
			request.memberCount(),
			request.profileStatus()
		);
		request.clubTypes().forEach(type -> {
			ClubType clubType = new ClubType(type);
			clubType.updateClub(club);
			clubTypeRepository.save(clubType);
		});
	}

	private Club findClubById(Long clubId) {
		return clubRepository.findById(clubId)
			.orElseThrow(() -> new OrganizationException(ClubErrorCode.CLUB_NOT_FOUND));
	}
}
