package com.course.webservices.restfulwebservices.user;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.core.ControllerEntityLinks;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
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

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User save = userDaoService.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        User user = userDaoService.deleteUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/users")
    public ResponseEntity retrieveUserParam(@RequestParam(name = "id", required = false) Integer id) {
        if (id == null) {
            return ResponseEntity.ok(userDaoService.findAll());
        }
        User one = userDaoService.findOne(id);
        if (one == null) {
            throw new UserNotFoundException("id: " + id);
        }

        return ResponseEntity.ok(one);
    }
}
