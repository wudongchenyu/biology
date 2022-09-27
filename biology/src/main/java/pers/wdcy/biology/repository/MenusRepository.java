package pers.wdcy.biology.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pers.wdcy.biology.entity.Menus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenusRepository extends R2dbcRepository<Menus, String>{

	@Query(value = "update public.biology_menus set enabled = false where code = :code ")
	Mono<Boolean> recycle(String code);

	@Query(value = "update public.biology_menus set enabled = true where code = :code ")
	Mono<Boolean> recover(String code);
	
	Flux<Menus> findAllByLevel(Integer level);

}
