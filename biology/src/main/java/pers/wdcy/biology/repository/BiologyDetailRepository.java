package pers.wdcy.biology.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pers.wdcy.biology.entity.BiologyDetail;
import reactor.core.publisher.Mono;

public interface BiologyDetailRepository extends R2dbcRepository<BiologyDetail, String>{

	@Query(value = "update public.biology_detail set enabled = true where code = :code ")
	Mono<Boolean> recycle(String code);

	@Query(value = "update public.biology_detail set enabled = false where code = :code ")
	Mono<Boolean> recover(String code);

}
