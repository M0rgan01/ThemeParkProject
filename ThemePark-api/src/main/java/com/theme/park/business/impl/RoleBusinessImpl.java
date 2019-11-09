package com.theme.park.business.impl;

import com.theme.park.business.RoleBusiness;
import com.theme.park.doa.RoleRepository;
import com.theme.park.entities.Role;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleBusinessImpl implements RoleBusiness {

    private static final Logger logger = LoggerFactory.getLogger(RoleBusinessImpl.class);
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) throws AlreadyExistException {

        if (roleRepository.findByName(role.getName()).isPresent())
            throw new AlreadyExistException("role.name.already.exist");

        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long id) throws NotFoundException {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("role.id.incorrect"));
    }

    @Override
    public Role getUserRole() throws NotFoundException {
        logger.debug("Get roles for user");
        return roleRepository.findByName("USER").orElseThrow(() -> new NotFoundException("role.user.not.found"));
    }

    @Override
    public List<Role> getAdminRole() throws NotFoundException {

        Role user = roleRepository.findByName("USER").orElseThrow(() -> new NotFoundException("role.user.not.found"));
        Role admin = roleRepository.findByName("ADMIN").orElseThrow(() -> new NotFoundException("role.admin.not.found"));

        List<Role> roles = new ArrayList<>();

        roles.add(user);
        roles.add(admin);
        logger.debug("Get roles for admin");
        return roles;
    }
}
