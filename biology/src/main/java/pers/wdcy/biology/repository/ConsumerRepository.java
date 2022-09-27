package pers.wdcy.biology.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pers.wdcy.biology.entity.Consumer;

public interface ConsumerRepository extends R2dbcRepository<Consumer, String>{

}
