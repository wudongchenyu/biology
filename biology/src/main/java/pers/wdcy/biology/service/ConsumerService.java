package pers.wdcy.biology.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wdcy.result.PageResult;
import com.wdcy.result.Paramer;
import com.wdcy.result.Result;

import pers.wdcy.biology.entity.Consumer;
import reactor.core.publisher.Mono;

@Service
public class ConsumerService {

	public Mono<Result<Boolean>> register(Paramer<String> paramer) {
		return null;
	}

	public Mono<Result<Boolean>> update(Paramer<String> paramer) {
		return null;
	}

	public Mono<Result<Boolean>> delete(String id) {
		return null;
	}

	public Mono<Result<Consumer>> findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Mono<Result<List<Consumer>>> findList(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Mono<PageResult<Boolean>> findPage(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
