package com.solutis.helpdesk.service.user.controller;

import com.solutis.helpdesk.service.user.service.LoadDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load=data")
public class LoadDatabaseController {
    @Autowired
    private LoadDatabaseService loadDatabaseService;

    @GetMapping
    public ResponseEntity<Void> loadDatabase() {
        loadDatabaseService.load();
        return ResponseEntity.ok().build();
    }
}
