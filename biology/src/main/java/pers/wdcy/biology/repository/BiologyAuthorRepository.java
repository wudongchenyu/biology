package pers.wdcy.biology.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pers.wdcy.biology.entity.BiologyAuthor;
import reactor.core.publisher.Mono;

public interface BiologyAuthorRepository extends R2dbcRepository<BiologyAuthor, String>{

	@Query(value = "update public.biology_author set enabled = true where code = :code ")
	Mono<Boolean> recycle(String code);

	@Query(value = "update public.biology_author set enabled = false where code = :code ")
	Mono<Boolean> recover(String code);
	
}
