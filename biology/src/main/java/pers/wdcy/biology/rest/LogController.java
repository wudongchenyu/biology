package pers.wdcy.biology.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wdcy.result.Result;

import pers.wdcy.biology.service.LogService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "log", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class LogController {
	
	private @Autowired LogService logService;
	
	@PostMapping(path = "in")
	public Mono<Result<String>> login() {
		return logService.login();
	}
	
	@PostMapping(path = "out")
	public Mono<Result<Boolean>> logout() {
		return logService.logout();
	}

}
