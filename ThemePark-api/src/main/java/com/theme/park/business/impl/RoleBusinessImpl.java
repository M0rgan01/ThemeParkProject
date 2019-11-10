package com.theme.park.business.impl;

import com.theme.park.business.RoleBusiness;
import com.theme.park.doa.RoleRepository;
import com.theme.park.entities.Role;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RoleBusinessImpl implements RoleBusiness {

    private static final Logger logger = LoggerFactory.getLogger(RoleBusinessImpl.class);
    private RoleRepository roleRepository;

    public RoleBusinessImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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
        logger.debug("Get USER role");
        return roleRepository.findByName("USER").orElseThrow(() -> new NotFoundException("role.user.not.found"));
    }

    @Override
    public Role getAdminRole() throws NotFoundException {
        logger.debug("Get ADMIN role");
        return roleRepository.findByName("USER").orElseThrow(() -> new NotFoundException("role.user.not.found"));
    }
}
