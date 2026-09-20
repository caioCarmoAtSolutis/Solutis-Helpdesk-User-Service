package com.solutis.helpdesk.service.user.service;

import com.solutis.helpdesk.service.user.domain.dto.*;
import com.solutis.helpdesk.service.user.domain.model.User;
import com.solutis.helpdesk.service.user.domain.model.UserRole;
import com.solutis.helpdesk.service.user.infrastructure.exception.EmailAddressUnavailableException;
import com.solutis.helpdesk.service.user.repository.UserRepository;
import com.solutis.helpdesk.service.user.repository.UserRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public DetailedUserData createUser(UserData data) {
        var userRole = getUserRole(data.role());
        validateEmail(data.email());
        var user = new User(data, userRole);
        user = userRepository.save(user);
        return new DetailedUserData(user);
    }

    public DetailedUserData createUser(UUID userId, UserData data) {
        var userRole = getUserRole(data.role());
        validateEmail(data.email());
        var user = new User(userId, data, userRole);
        user = userRepository.save(user);
        return new DetailedUserData(user);
    }

    public Page<ListUserData> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(ListUserData::new);
    }

    public ListUserData getUser(UUID id) {
        return new ListUserData(getUserById(id));
    }

    public DetailedUserData updateUser(UUID id, UserData data) {
        var userRole = getUserRole(data.role());
        validateEmail(data.email());
        User user = getUserById(id);
        user.update(data, userRole);
        user = userRepository.save(user);
        return new DetailedUserData(user);
    }

    public UserActivityStatusData deactivateUser(UUID id) {
        User user = getUserById(id);
        user.deactivate();
        user = userRepository.save(user);
        return new UserActivityStatusData(user);
    }

    public UserActivityStatusData activateUser(UUID id) {
        User user = getUserById(id);
        user.activate();
        user = userRepository.save(user);
        return new UserActivityStatusData(user);
    }

    public User getUserById(UUID id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty())
            throw new EntityNotFoundException("User entity with id " + id.toString() + " not found!");
        return optional.get();
    }

    private UserRole getUserRole(UserRoleData data) {
        Optional<UserRole> optional = userRoleRepository.findByRole(data.role());
        if (optional.isEmpty())
            throw new EntityNotFoundException("Role entity " + data.role().toString() + " not found!");
        return optional.get();
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new EmailAddressUnavailableException("The following email address " + email + " is being used, please try to login!");
    }

    public void deleteUser(UUID id) {
        getUserById(id);
        userRepository.deleteById(id);
    }
}
