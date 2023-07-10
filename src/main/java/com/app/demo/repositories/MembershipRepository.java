package com.app.demo.repositories;

import com.app.demo.dao.MembershipDao;
import com.app.demo.entities.MembershipEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional()
public class MembershipRepository  implements MembershipDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MembershipEntity createMembership(MembershipEntity membership) {
        return entityManager.merge(membership);
    }

    @Override
    public MembershipEntity findMembershipById(int id) {
        return entityManager.find(MembershipEntity.class, id);
    }

    @Override
    public List<MembershipEntity> getAllMemberships() {
        return entityManager.createQuery("FROM MembershipEntity").getResultList();
    }

    @Override
    public MembershipEntity updateMembership(MembershipEntity membership) {
        return this.createMembership(membership);
    }

    @Override
    public void deleteMembership(MembershipEntity membership) {
        entityManager.remove(membership);
    }
}
