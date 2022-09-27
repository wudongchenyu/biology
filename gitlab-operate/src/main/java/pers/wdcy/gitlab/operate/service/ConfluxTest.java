package pers.wdcy.gitlab.operate.service;

import java.net.URI;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfluxTest {
	
	public static void main(String[] args) {
		Builder builder = WebClient.builder();
		builder.baseUrl("https://www.confluxscan.net")
		.build()
		.get()
		.uri(URI.create("/address/cfx:aapgt3czhv6pgymy1bu2f7njrp0b1be10jykx73dwv?limit=10&reverse=true&skip=460&to=cfx%3Aacd1sx1rtgd6nt52m0aax38m9jy5d0x10a1afs498a"))
		.retrieve()
		.bodyToMono(Object.class)
		.subscribe(o -> log.info("{}", o));
	}

}
