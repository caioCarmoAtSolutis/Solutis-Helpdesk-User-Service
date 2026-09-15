package com.solutis.helpdesk.service.user.service;

import com.solutis.helpdesk.service.user.repository.UserRepository;
import com.solutis.helpdesk.service.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    
}
