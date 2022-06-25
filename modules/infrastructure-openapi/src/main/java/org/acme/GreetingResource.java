package org.acme;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public final class GreetingResource {

    /**
     * Hello
     * @return Hello message
     */
    @GetMapping
    public String hello() {
        return "Hello Spring";
    }
}
