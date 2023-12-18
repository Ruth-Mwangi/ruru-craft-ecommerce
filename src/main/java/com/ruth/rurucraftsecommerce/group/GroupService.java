package com.ruth.rurucraftsecommerce.group;

import java.util.List;

public interface GroupService {

    Group createGroup(Group group) throws IllegalAccessException;
    Group getGroupById(Integer id);
    List<Group> getAllGroups();
    Group updateGroup(Integer id,Group group) throws IllegalAccessException;
    boolean deleteGroup(Integer id) throws IllegalAccessException;
}
