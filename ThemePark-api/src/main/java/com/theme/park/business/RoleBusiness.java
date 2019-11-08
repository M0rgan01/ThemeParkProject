package com.theme.park.business;

import com.theme.park.entities.Role;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.NotFoundException;

import javax.security.auth.login.AccountException;
import java.util.List;

public interface RoleBusiness {
    /** Création d'un role
     *
     * @param role
     * @return Role
     * @throws AccountException
     */
    Role createRole(Role role) throws AlreadyExistException;

    /** Récupération d'un role par son ID
     *
     * @param id
     * @return Role
     * @throws AccountException
     */
    Role getRoleById(Long id) throws NotFoundException;

    /** Récupération du role d'un utilisateur
     *
     * @return Role
     * @throws AccountException
     */
    Role getUserRole() throws NotFoundException;

    /** Récupération des roles d'un administrateur
     *
     * @return List Role
     * @throws AccountException
     */
    List<Role> getAdminRole() throws NotFoundException;
}
