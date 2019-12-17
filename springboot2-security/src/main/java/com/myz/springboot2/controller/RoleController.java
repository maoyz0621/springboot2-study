package com.myz.springboot2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maoyz
 */
@RestController
@RequestMapping(value = "/roles")
@PreAuthorize(value = "hasRole('ROLE')")
public class RoleController {

    @RequestMapping(method = RequestMethod.GET)
    public List<String> getUsers() {
        return new ArrayList<>();
    }

}
