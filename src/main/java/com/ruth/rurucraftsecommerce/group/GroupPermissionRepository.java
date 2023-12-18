package com.ruth.rurucraftsecommerce.group;

import com.ruth.rurucraftsecommerce.common.BaseRepository;
import com.ruth.rurucraftsecommerce.permissions.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupPermissionRepository extends BaseRepository<GroupPermission> {
    boolean existsByGroupAndPermissionIn(Group group, List<Permission> permissions);

    Optional<GroupPermission> findByGroupIdAndPermissionId(Integer groupId, Integer permissionId);
}
