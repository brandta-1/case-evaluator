package com.crates.value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/containers")
public class ContainerController {
    @Autowired
    private final ContainerRepository containers;

    public ContainerController(ContainerRepository containerRepository) {
        this.containers = containerRepository;
    }
    @GetMapping
    public Iterable<ContainerSimple> getAllContainers() {
        return containers.findAllWithoutLinks();
    }
}
