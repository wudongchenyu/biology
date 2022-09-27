package pers.wdcy.gitlab.operate.service;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.TreeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import pers.wdcy.gitlab.operate.repository.GitlabBranchRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
public class GitlabOperateService {

	public static String hostUrl;

	public static String personalAccessToken;

	private @Autowired GitlabBranchRepository gitlabBranchRepository;

	private AtomicLong number = new AtomicLong(0);

	public Mono<Boolean> name() {
		return Mono.just(new GitLabApi(hostUrl, personalAccessToken)).flatMap(
				api -> gitlabBranchRepository.findAll().parallel(10).runOn(Schedulers.boundedElastic()).map(branch -> {
					log.info("计数：{}", number.addAndGet(1));
					try {
						return api.getRepositoryApi().getTree(api, personalAccessToken, hostUrl).stream()
								.filter(item -> item.getPath().contains("pom.xml")).collect(Collectors.toList());
					} catch (GitLabApiException e) {
						e.printStackTrace();
					}
					return new ArrayList<TreeItem>();
				}).filter(items -> items.size() > 0).sequential().flatMapIterable(Function.identity()).map(item -> item)
						.map(item -> StringUtils.hasText(item.getId())).reduce(true, (x, y) -> x && y)
						.doFinally(a -> api.close())

		);
 
	}

}
