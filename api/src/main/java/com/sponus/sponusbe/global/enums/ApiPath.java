package com.sponus.sponusbe.global.enums;

public class ApiPath {
	private static final String prefix = "/api/v2";
	public static final String AUTH_URI = prefix + "/auth";
	public static final String ANNOUNCEMENT_URI = prefix + "/me/announcements";
	public static final String NOTIFICATION_URI = prefix + "/notification";
	public static final String COMPANY_URI = prefix + "/companies";
	public static final String CLUB_URI = prefix + "/clubs";
	public static final String ORGANIZATION_URI = prefix + "/organizations";
	public static final String ORGANIZATION_LINK_URI = prefix + "/organization-links";
	public static final String PORTFOLIO_URI = prefix + "/portfolio";
	public static final String PROPOSE_URI = prefix + "/proposes";
	public static final String S3_URI = prefix + "/s3";
}
