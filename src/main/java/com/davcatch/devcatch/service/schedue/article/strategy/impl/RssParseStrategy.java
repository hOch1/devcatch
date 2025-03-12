package com.davcatch.devcatch.service.schedue.article.strategy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.davcatch.devcatch.domain.ParseMethod;
import com.davcatch.devcatch.domain.Source;
import com.davcatch.devcatch.exception.CustomException;
import com.davcatch.devcatch.integration.rss.RssReader;
import com.davcatch.devcatch.service.schedue.article.dto.ParsedArticle;
import com.davcatch.devcatch.service.schedue.article.extractor.ContentExtractor;
import com.davcatch.devcatch.service.schedue.article.extractor.ContentExtractorFactory;
import com.davcatch.devcatch.service.schedue.article.strategy.AbstractArticleStrategy;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RssParseStrategy extends AbstractArticleStrategy {

	public RssParseStrategy(RssReader rssReader, ContentExtractorFactory contentExtractorFactory) {
		super(rssReader, contentExtractorFactory);
	}

	@Override
	public List<ParsedArticle> process(Source source) throws CustomException {
		ContentExtractor extractor = getContentExtractor(source.getParseMethod());
		List<SyndEntry> entries = getEntries(source);

		List<ParsedArticle> parsedArticles = new ArrayList<>();
		for (SyndEntry entry : entries) {
			String content = extractor.extractContent(entry, null);

			parsedArticles.add(ParsedArticle.of(content, entry, source.isUseLink()));
		}

		return parsedArticles;
	}

	@Override
	public Set<ParseMethod> getSupportedParseMethods() {
		return Set.of(ParseMethod.RSS_DESCRIPTION, ParseMethod.RSS_CONTENT);
	}
}
