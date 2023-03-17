package com.edu.ecommerce.service.interfaces;

import com.edu.ecommerce.model.UserRole;

import java.util.List;

/**
 * The com.edu.fileservice.service to the maintain operations with user permission.
 */
public interface RoleService {

    /**
     * Returns all roles.
     *
     * @return list of roles
     */
    List<UserRole> findAll();

    /**
     * Return role for transmitted id.
     *
     * @param id for find role
     * @return role for transmitted parameter
     */
    UserRole findById(Long id);


  /**
   * Return role for transmitted email.
   *
   * @param email for find role
   * @return role for transmitted parameter
   */
  UserRole findByUserEmail(String email);

    /**
     * Return role transmitted name.
     *
     * @param name for find role
     * @return role for transmitted parameter
     */
    UserRole findByName(String name);

    /**
     * Saves transmitted role.
     *
     * @param role for create
     * @return created role
     */
    UserRole create(UserRole role);

    /**
     * Updates transmitted role.
     *
     * @param id   role id for update
     * @param role for update
     * @return updated permission
     */
    UserRole update(Long id, UserRole role);

    /**
     * Deletes role by id.
     */
    void delete(Long id);
}
