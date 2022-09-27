package pers.wdcy.gitlab.operate.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pers.wdcy.gitlab.operate.entity.GitlabBranch;

public interface GitlabBranchRepository extends R2dbcRepository<GitlabBranch, Long>{

}
