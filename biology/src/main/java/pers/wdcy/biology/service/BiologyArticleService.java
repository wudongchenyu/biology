package pers.wdcy.biology.service;

import static com.wdcy.result.ResultUtils.error;
import static com.wdcy.result.ResultUtils.failed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wdcy.result.PageModel;
import com.wdcy.result.PageResult;
import com.wdcy.result.Result;
import com.wdcy.result.ResultUtils;

import lombok.extern.slf4j.Slf4j;
import pers.wdcy.biology.entity.BiologyArticle;
import pers.wdcy.biology.model.BiologyArticleRevise;
import pers.wdcy.biology.model.BiologyArticleSearch;
import pers.wdcy.biology.repository.BiologyArticleRepository;
import pers.wdcy.biology.util.IdUtils;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Slf4j
@Service
public class BiologyArticleService {
	
	private @Autowired BiologyArticleRepository articleRepository;
	
	private @Autowired R2dbcEntityTemplate template;

	public Mono<Result<Boolean>> register(BiologyArticle article) {
		return Mono.just(article)
				.map(a -> {
					a.setCode(IdUtils.id());
					a.setFresh(true);
					a.setCreationTime(LocalDateTime.now());
					return a;
				})
				.flatMap(articleRepository::save)
				.map(BiologyArticle::getCode)
				.map(StringUtils::hasText)
				.map(ResultUtils::success);
	}

	public Mono<Result<Boolean>> delete(String code) {
		return articleRepository.deleteById(code).then(Mono.just(ResultUtils.success(true)))
				.switchIfEmpty(Mono.just(ResultUtils.success(true))).doOnError(error -> log.error("删除艺术品失败", error))
				.onErrorResume(error -> Mono.just(failed("删除艺术品失败", false)));
	}
	
	public Mono<Result<Boolean>> revise(BiologyArticleRevise revise) {
		return articleRepository.findById(revise.getCode()).map(spe -> {
			BeanUtils.copyProperties(revise, spe);
			spe.setFresh(false);
			spe.setOperationTime(LocalDateTime.now());
			spe.setOperator("");
			return spe; 
		}).flatMap(articleRepository::save).map(BiologyArticle::getCode).map(StringUtils::hasText)
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("修改艺术品失败", error))
				.onErrorResume(error -> Mono.just(error("修改艺术品失败", false)));
	}

	public Mono<Result<Boolean>> recycle(String code) {
		return articleRepository.recycle(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("回收艺术品失败", error))
				.onErrorResume(error -> Mono.just(error("回收艺术品失败", false)));
	}

	public Mono<Result<Boolean>> recover(String code) {
		return articleRepository.recover(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("恢复艺术品失败", error))
				.onErrorResume(error -> Mono.just(error("恢复艺术品失败", false)));
	}

	public Mono<Result<BiologyArticle>> findDetail(String code) {
		return articleRepository.findById(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("艺术品详情查询失败", error))
				.onErrorResume(error -> Mono.just(error("艺术品详情查询失败", null)));
	}

	public Mono<Result<List<BiologyArticle>>> findList(BiologyArticleSearch species) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());
		this.buildCriteria(species, criterias);

		return template.select(Query.query(CriteriaDefinition.from(criterias)), BiologyArticle.class).collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("艺术品列表查询失败", error))
				.onErrorResume(error -> Mono.just(error("艺术品列表查询失败", null)));
	}

	public Mono<PageResult<List<BiologyArticle>>> findPage(PageModel<BiologyArticleSearch> search) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		this.buildCriteria(search.getParamer(), criterias);
		
		return template
				.select(Query.query(CriteriaDefinition.from(criterias)).offset(search.offset()).limit(search.getSize()),
						BiologyArticle.class)
				.collectList()
				.zipWith(template.count(Query.query(CriteriaDefinition.from(criterias)), BiologyArticle.class))
				.map(TupleUtils.function((bs, count) -> {
					return new PageResult<List<BiologyArticle>>(ResultUtils.success(bs), search.getCurrent(),
							search.getSize(), count);
				}))
				.switchIfEmpty(Mono.just(new PageResult<List<BiologyArticle>>(ResultUtils.losing(), search.getCurrent(),
						search.getSize(), 0)))
				.doOnError(error -> log.error("艺术品列表查询失败", error)).onErrorResume(
						error -> Mono.just(new PageResult<List<BiologyArticle>>(ResultUtils.error("艺术品列表查询失败", null),
								search.getCurrent(), search.getSize(), 0)));
	}

	private void buildCriteria(BiologyArticleSearch article, List<Criteria> criterias) {
		
		if (Objects.nonNull(article) && StringUtils.hasText(article.getArticleName())) {
			criterias.add(Criteria.where("article_name").is(article.getArticleName()));
		}
		
		if (Objects.nonNull(article) && StringUtils.hasText(article.getAuthor())) {
			criterias.add(Criteria.where("author").is(article.getAuthor()));
		}
		
		if (Objects.nonNull(article) && StringUtils.hasText(article.getBiologyCode())) {
			criterias.add(Criteria.where("biology_code").is(article.getBiologyCode()));
		}
		
		if (Objects.nonNull(article) && StringUtils.hasText(article.getIntro())) {
			criterias.add(Criteria.where("intro").is(article.getIntro()));
		}
		
		if (Objects.nonNull(article) && Objects.nonNull(article.getAffiliation()) && article.getAffiliation() > 0) {
			criterias.add(Criteria.where("affiliation").is(article.getAffiliation()));
		}
		
		if (Objects.nonNull(article) && Objects.nonNull(article.getSalesStatus()) && article.getSalesStatus() > 0) {
			criterias.add(Criteria.where("sales_status").is(article.getSalesStatus()));
		}
		
		if (Objects.nonNull(article) && Objects.nonNull(article.getArticleType()) && article.getArticleType() > 0) {
			criterias.add(Criteria.where("article_type").is(article.getArticleType()));
		}
	}
}
