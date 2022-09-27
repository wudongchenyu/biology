package pers.wdcy.operate.chrome.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloWordController {
	
	@GetMapping(path = "/")
	public Mono<String> helloMono() {
		return Mono.just("hello world");
	}
	
	@GetMapping(path = "/flux")
	public Flux<String> helloFlux() {
		return Flux.just("hello world");
	}

}
