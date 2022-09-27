package pers.wdcy.biology.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wdcy.result.PageResult;
import com.wdcy.result.Paramer;
import com.wdcy.result.Result;

import io.swagger.v3.oas.annotations.tags.Tag;
import pers.wdcy.biology.entity.Consumer;
import pers.wdcy.biology.service.ConsumerService;
import reactor.core.publisher.Mono;

@Tag(name = "客户")
@RestController
@RequestMapping(path = "consumer", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class ConsumerController {
	
	private @Autowired ConsumerService consumerService;
	
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody Paramer<String> paramer) {
		return consumerService.register(paramer);
	}
	
	@PostMapping(path = "update")
	public Mono<Result<Boolean>> update(@RequestBody Paramer<String> paramer) {
		return consumerService.update(paramer);
	}
	
	@PostMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String id) {
		return consumerService.delete(id);
	}
	
	@PostMapping(path = "find/one")
	public Mono<Result<Consumer>> findOne(@RequestParam String id) {
		return consumerService.findOne(id);
	}
	
	@PostMapping(path = "find/list")
	public Mono<Result<List<Consumer>>> findList(@RequestParam String id) {
		return consumerService.findList(id);
	}
	
	@PostMapping(path = "find/page")
	public Mono<PageResult<Boolean>> findPage(@RequestParam String id) {
		return consumerService.findPage(id);
	}

}
