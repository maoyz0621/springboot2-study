package com.myz.springboot2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * role限定 @PreAuthorize
 *
 * @author maoyz
 */
@RestController
@RequestMapping(value = "/users")
@PreAuthorize(value = "hasAuthority('ROEL_ADMIN')")
public class UserController {

    @RequestMapping(method = RequestMethod.GET)
    public List<String> getUsers() {
        return new ArrayList<>();
    }

}
