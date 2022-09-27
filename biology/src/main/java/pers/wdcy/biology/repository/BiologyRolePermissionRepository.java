package pers.wdcy.biology.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import pers.wdcy.biology.entity.BiologyRolePermission;

public interface BiologyRolePermissionRepository extends R2dbcRepository<BiologyRolePermission, String>{

}
