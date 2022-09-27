package pers.wdcy.biology.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pers.wdcy.biology.entity.BiologyRole;
import reactor.core.publisher.Mono;

public interface BiologyRoleRepository extends R2dbcRepository<BiologyRole, String>{


	@Query(value = "update public.biology_role set enabled = true where code = :code ")
	Mono<Boolean> recycle(String code);

	@Query(value = "update public.biology_role set enabled = false where code = :code ")
	Mono<Boolean> recover(String code);

}
