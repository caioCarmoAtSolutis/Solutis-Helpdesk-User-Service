package com.solutis.helpdesk.service.user.service;

import com.solutis.helpdesk.service.user.domain.dto.DetailedUserData;
import com.solutis.helpdesk.service.user.domain.dto.UserData;
import com.solutis.helpdesk.service.user.domain.dto.UserRoleData;
import com.solutis.helpdesk.service.user.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoadDatabaseService {
    @Autowired
    private UserService userService;

    public List<DetailedUserData> load() {

        var list = new ArrayList<DetailedUserData>();

        list.add(userService.createUser(
                UUID.fromString("281b7dfd-3b7b-471e-9147-c25744ceb466"),
                new UserData("Admin",
                        "admin@example.com",
                        new UserRoleData(Role.ADMIN)))
        );
        list.add(userService.createUser(
                UUID.fromString("7a8b9c0d-1e2f-3a4b-5c6d-7e8f9a0b1c2d"),
                new UserData("Bob Johnson",
                        "bob.johnson@example.com",
                        new UserRoleData(Role.TECHNICIAN)))
        );
        list.add(userService.createUser(
                UUID.fromString("0f9e8d7c-6b5a-4f3e-2d1c-0b9a8f7e6d5c"),
                new UserData("Charlie Brown",
                        "charlie.brown@example.com",
                        new UserRoleData(Role.TECHNICIAN)))
        );
        list.add(userService.createUser(
                UUID.fromString("2c3d4e5f-6a7b-8c9d-0e1f-2a3b4c5d6e7f"),
                new UserData("Diana Prince",
                        "diana.prince@example.com",
                        new UserRoleData(Role.TECHNICIAN)))
        );
        list.add(userService.createUser(
                UUID.fromString("e1f2a3b4-c5d6-7e8f-9a0b-1c2d3e4f5a6b"),
                new UserData("Evan Wright",
                        "evan.wright@example.com",
                        new UserRoleData(Role.TECHNICIAN)))
        );
        list.add(userService.createUser(
                UUID.fromString("e2a0f821-6b4d-4b8a-921d-78a2e12a0f10"),
                new UserData("Fiona Gallagher",
                        "fiona.gallagher@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"),
                new UserData("George Clark",
                        "george.clark@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("9d8c7b6a-5e4d-3c2b-1a0f-9e8d7c6b5a4f"),
                new UserData("Hannah Abbott",
                        "hannah.abbott@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("8e7f6a5b-4c3d-2e1f-0a9b-8c7d6e5f4a3b"),
                new UserData("Ian Malcolm",
                        "ian.malcolm@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("b5a4f3e2-d1c0-9b8a-7f6e-5d4c3b2a10fe"),
                new UserData("Julia Roberts",
                        "julia.roberts@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("4c5d6e7f-8a9b-0c1d-2e3f-4a5b6c7d8e9f"),
                new UserData("Kevin Bacon",
                        "kevin.bacon@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("f9e8d7c6-b5a4-3f2e-1d0c-9b8a7f6e5d4c"),
                new UserData("Laura Croft",
                        "laura.croft@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("3a4b5c6d-7e8f-9a0b-1c2d-3e4f5a6b7c8d"),
                new UserData("Michael Scott",
                        "michael.scott@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("8f7e6d5c-4b3a-2f1e-0d9c-8b7a6f5e4d3c"),
                new UserData("Nina Williams",
                        "nina.williams@example.com",
                        new UserRoleData(Role.CLIENT)))
        );
        list.add(userService.createUser(
                UUID.fromString("1c2d3e4f-5a6b-7c8d-9e0f-1a2b3c4d5e6f"),
                new UserData("Oscar Martinez",
                        "oscar.martinez@example.com",
                        new UserRoleData(Role.CLIENT)))
        );

        return list;
    }
}
