package com.sponus.sponusbe.domain.organization.club;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Club;
import com.sponus.coredomain.domain.organization.repository.ClubRepository;
import com.sponus.sponusbe.domain.organization.exception.ClubErrorCode;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {
	private final ClubRepository clubRepository;

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
			request.clubType(),
			request.profileStatus()
		);
	}

	public void deleteClub(Long clubId) {
		final Club club = findClubById(clubId);
		club.delete();
	}

	private Club findClubById(Long clubId) {
		return clubRepository.findById(clubId)
			.orElseThrow(() -> new OrganizationException(ClubErrorCode.CLUB_NOT_FOUND));
	}
}
