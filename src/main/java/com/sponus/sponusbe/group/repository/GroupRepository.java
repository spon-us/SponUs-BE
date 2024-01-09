package com.sponus.sponusbe.group.repository;

import com.sponus.sponusbe.group.entity.Group;

public interface GroupRepository {

	Group save(Group group);

	Group findGroupByEmail(String groupEmail);
}
