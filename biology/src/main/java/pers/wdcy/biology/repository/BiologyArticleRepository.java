package pers.wdcy.biology.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pers.wdcy.biology.entity.BiologyArticle;
import reactor.core.publisher.Mono;

public interface BiologyArticleRepository extends R2dbcRepository<BiologyArticle, String>{

	@Query(value = "update public.biology_article set enabled = true where code = :code ")
	Mono<Boolean> recycle(String code);

	@Query(value = "update public.biology_article set enabled = false where code = :code ")
	Mono<Boolean> recover(String code);
	
}
