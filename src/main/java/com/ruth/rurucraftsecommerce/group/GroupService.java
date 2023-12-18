package com.ruth.rurucraftsecommerce.group;

import com.ruth.rurucraftsecommerce.permissions.Permission;

import java.util.List;

public interface GroupService {

    Group createGroup(Group group) throws IllegalAccessException;
    Group getGroupById(Integer id);
    List<Group> getAllGroups();
    Group updateGroup(Integer id,Group group) throws IllegalAccessException;
    boolean deleteGroup(Integer id) throws IllegalAccessException;
    boolean createGroupWithPermissions(Integer groupId, List<Integer> permissionIds) throws IllegalAccessException;

    boolean deletGroupPermissions(Integer groupId, Integer permissionId);

    GroupPermission findByGroupIdAndPermissionId(Integer groupId,Integer permissionId);
    List<Permission> getPermissionsByGroupId(Integer groupId);
}
