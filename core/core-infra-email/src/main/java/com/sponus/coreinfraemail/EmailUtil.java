package com.sponus.coreinfraemail;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.sponus.coreinfraredis.util.RedisUtil;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailUtil {

	private final JavaMailSender emailSender;
	private final RedisUtil redisUtil;

	private static final Random RANDOM = new Random();

	public String sendEmail(String to) throws Exception {
		String code = createEmailCode();
		MimeMessage message = createEmail(to, code);
		emailSender.send(message);

		redisUtil.saveAsValue(
			to + "_code_expired",
			code,
			300L,
			TimeUnit.SECONDS
		);

		return code;
	}

	private MimeMessage createEmail(String to, String code) throws Exception {
		log.info("보내는 대상 : " + to);
		log.info("인증 코드 : " + code);
		MimeMessage message = emailSender.createMimeMessage();
		InternetAddress[] recipients = {new InternetAddress(to)};
		message.setRecipients(Message.RecipientType.TO, recipients); // 보내는 대상
		message.setSubject("Spon-us 인증 코드 발급입니다."); // 제목

		String msgg = "";
		msgg += "<div style='margin:20px;'>";
		msgg += "<h1> Spon-us 인증 코드입니다. </h1>"; // 동적으로 메시지 내용 변경
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'> 인증 코드 </h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "인증 코드 : <strong>";
		msgg += code + "</strong><div><br/> ";
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html"); // 내용
		message.setFrom(new InternetAddress("Spon-us_team", "Spon-us")); // 보내는 사람

		return message;
	}

	public static String createEmailCode() {
		StringBuilder code = new StringBuilder();

		for (int i = 0; i < 6; i++) { // 인증 코드 6자리
			int index = RANDOM.nextInt(3); // 0~2 까지 랜덤
			switch (index) {
				case 0:
					code.append((char)((RANDOM.nextInt(26)) + 97));
					//  a~z  (ex. 1+97=98 => (char)98 = 'b')
					break;
				case 1:
					code.append((char)((RANDOM.nextInt(26)) + 65));
					//  A~Z
					break;
				default:
					code.append((RANDOM.nextInt(10)));
					// 0~9
					break;
			}
		}
		return code.toString();
	}
}
