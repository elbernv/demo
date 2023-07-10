package com.app.demo.services;

import com.app.demo.entities.MembershipEntity;
import com.app.demo.repositories.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@Service
public class MembershipService {
    @Autowired
    private MembershipRepository membershipRepository;

    public MembershipEntity createMembership(MembershipEntity membership){
        return this.membershipRepository.createMembership(membership);
    }

    public List<MembershipEntity> getAllMemberships(){
        return this.membershipRepository.getAllMemberships();
    }

    public MembershipEntity updateMembership(MembershipEntity membership, int id){
        this.validateMembership(id);
        membership.setId(id);
        return this.membershipRepository.updateMembership(membership);
    }

    public MembershipEntity validateMembership(int id){
        MembershipEntity membership = this.membershipRepository.findMembershipById(id);

        if(membership == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "membership Not Found");
        }

        return membership;
    }

    public MembershipEntity getMembershipById(int id){
        return this.validateMembership(id);
    }

    public HashMap<String, String> deleteMembership(int id){
        try {
            MembershipEntity membership = this.validateMembership(id);
            this.membershipRepository.deleteMembership(membership);
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "membership deleted");

            return response;
        }
        catch (DataIntegrityViolationException err){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "some customers depend on this membership, it cannot be deleted.");
        }
    }
}
