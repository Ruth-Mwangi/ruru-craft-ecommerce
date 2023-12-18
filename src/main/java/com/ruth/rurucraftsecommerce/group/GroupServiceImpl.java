package com.ruth.rurucraftsecommerce.group;

import com.ruth.rurucraftsecommerce.permissions.Permission;
import com.ruth.rurucraftsecommerce.permissions.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupPermissionRepository groupPermissionRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Group createGroup(Group group) throws IllegalArgumentException {

        try {
            return groupRepository.save(group);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Group not saved.");

        }


    }

    @Override
    public Group getGroupById(Integer id) {

        return groupRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such element with id : "+id));
    }

    @Override
    public List<Group> getAllGroups() {

        return groupRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Group updateGroup(Integer id, Group group) throws IllegalArgumentException {

        Group retrieveGroup=groupRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such element with id : "+id));
        retrieveGroup.setName(group.getName());

        try {
            Group updatedGroup=groupRepository.save(retrieveGroup);
            return groupRepository.save(updatedGroup);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Group not updated.");

        }
    }

    @Override
    public boolean deleteGroup(Integer id) throws IllegalArgumentException {
        Group retrieveGroup=groupRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such element with id : "+id));
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());

        retrieveGroup.setDeletedAt(convertedDate);
        try {
            Group updatedGroup=groupRepository.save(retrieveGroup);
            return true;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Group not deleted.");

        }
    }

    @Override
    public boolean createGroupWithPermissions(Integer groupId, List<Integer> permissionIds) throws IllegalArgumentException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group not found with id: " + groupId));
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);

        if (groupPermissionRepository.existsByGroupAndPermissionIn(group, permissions)) {
            throw new IllegalArgumentException("Group and Permission combination already exists.");
        }

        List<GroupPermission> groupPermissions=permissions.stream().map(permission->new GroupPermission(group,permission)).toList();

        try {
            List<GroupPermission> groupPermission=groupPermissionRepository.saveAll(groupPermissions);
            return true;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Group permission not created.");

        }


    }


    @Override
    public boolean deletGroupPermissions(Integer groupId, Integer permissionId) {
        GroupPermission groupPermission=findByGroupIdAndPermissionId(groupId,permissionId);
        try {
            Group group = groupPermission.getGroup();
            group.getGroupPermissions().remove(groupPermission);
            groupPermissionRepository.delete(groupPermission);
            return !groupPermissionRepository.existsById(groupPermission.getId());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Group permission not deleted.");

        }

    }

    @Override
    public GroupPermission findByGroupIdAndPermissionId(Integer groupId, Integer permissionId) {
        return groupPermissionRepository.findByGroupIdAndPermissionId(groupId,permissionId).orElseThrow(()->new NoSuchElementException("The Combination does not exist"));
    }

    @Override
    public List<Permission> getPermissionsByGroupId(Integer groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found with id: " + groupId));
        return group.getGroupPermissions().stream()
                .map(GroupPermission::getPermission)
                .collect(Collectors.toList());
    }


}
