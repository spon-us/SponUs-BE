package com.sponus.sponusbe.domain.propose.dto.response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record DateGroupedProposeResponse(
	String createdDate,
	List<ProposeSummaryGetResponse> proposes
) {
	public static List<DateGroupedProposeResponse> from(List<ProposeSummaryGetResponse> proposes) {
		final Map<String, List<ProposeSummaryGetResponse>> groupedByDate = proposes.stream()
			.collect(Collectors.groupingBy(ProposeSummaryGetResponse::createdDate));

		// createdDate 내림차순으로 정렬된 결과를 List<DateGroupedProposeResponse>로 변환
		return groupedByDate.entrySet().stream()
			.sorted(Map.Entry.<String, List<ProposeSummaryGetResponse>>comparingByKey().reversed())
			.map(entry -> new DateGroupedProposeResponse(entry.getKey(), entry.getValue()))
			.toList();
	}
}
