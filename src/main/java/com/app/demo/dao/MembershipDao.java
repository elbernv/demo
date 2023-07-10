package com.app.demo.dao;


import com.app.demo.entities.MembershipEntity;

import java.util.HashMap;
import java.util.List;

public interface MembershipDao {
    MembershipEntity createMembership(MembershipEntity customer);

    MembershipEntity findMembershipById(int id);

    List<MembershipEntity> getAllMemberships();

    MembershipEntity updateMembership(MembershipEntity membership);

    void deleteMembership(MembershipEntity membership);
}
