package com.course.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("users")
public class UserResource {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        User one = userDaoService.findOne(id);
        if (one == null) {
            throw new UserNotFoundException("id: " + id);
        }

        Resource<User> resource = new Resource<User>(one);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User save = userDaoService.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        User user = userDaoService.deleteUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping(params = "id")
    public ResponseEntity retrieveUserParam(@RequestParam(name = "id") Integer id) {
        User one = userDaoService.findOne(id);
        if (one == null) {
            throw new UserNotFoundException("id: " + id);
        }

        return ResponseEntity.ok(one);
    }
}
