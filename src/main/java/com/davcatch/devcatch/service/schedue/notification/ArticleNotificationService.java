package com.davcatch.devcatch.service.schedue.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.davcatch.devcatch.domain.Article;
import com.davcatch.devcatch.domain.Member;
import com.davcatch.devcatch.repository.MemberRepository;
import com.davcatch.devcatch.service.article.ArticleService;
import com.davcatch.devcatch.service.mail.MailService;
import com.davcatch.devcatch.service.mail.MailTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleNotificationService {

	private final ArticleService articleService;
	private final MemberRepository memberRepository;
	private final MailService mailService;

	public void sendNewArticle() {
		List<Article> articles = articleService.findSendArticles();
		if (articles.isEmpty()) {
			log.info("보낼 새로운 Article이 없습니다");
			return;
		}
		log.info("새로운 Article 총 {}개 전송 시작", articles.size());

		List<Member> members = memberRepository.findAll();

		Context context = new Context();
		context.setVariable("articles", articles);

		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (Member member : members) {
			context.setVariable("email", member.getEmail());
			CompletableFuture<Void> future = mailService.sendMail(member.getEmail(), MailTemplate.SEND_ARTICLE, context);
			futures.add(future);
		}

		int failCount = 0;
		for (CompletableFuture<Void> future : futures) {
			try {
				future.get();
			} catch (Exception e) {
				failCount++;
			}
		}
		log.info("새로운 Article {}개, 회원 {}명에게 전송 완료, 실패 {}명", articles.size(), (members.size() - failCount), failCount);

		articles.forEach(article -> {
			article.sendArticle();
			articleService.save(article);
		});
	}
}
