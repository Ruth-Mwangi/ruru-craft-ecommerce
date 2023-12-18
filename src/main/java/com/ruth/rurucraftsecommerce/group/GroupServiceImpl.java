package com.ruth.rurucraftsecommerce.group;

import com.ruth.rurucraftsecommerce.permissions.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    GroupRepository groupRepository;

    @Override
    public Group createGroup(Group group) throws IllegalAccessException {

        try {
            return groupRepository.save(group);
        }catch (IllegalArgumentException e){
            throw new IllegalAccessException("Group not saved.");

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
    public Group updateGroup(Integer id, Group group) throws IllegalAccessException {

        Group retrieveGroup=groupRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such element with id : "+id));
        retrieveGroup.setName(group.getName());

        try {
            Group updatedGroup=groupRepository.save(retrieveGroup);
            return groupRepository.save(updatedGroup);
        }catch (IllegalArgumentException e){
            throw new IllegalAccessException("Group not updated.");

        }
    }

    @Override
    public boolean deleteGroup(Integer id) throws IllegalAccessException {
        Group retrieveGroup=groupRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such element with id : "+id));
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());

        retrieveGroup.setDeletedAt(convertedDate);
        try {
            Group updatedGroup=groupRepository.save(retrieveGroup);
            return true;
        }catch (IllegalArgumentException e){
            throw new IllegalAccessException("Group not deleted.");

        }
    }
}
