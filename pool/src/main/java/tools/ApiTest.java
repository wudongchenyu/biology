package tools;

import javax.annotation.PostConstruct;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.gitlab4j.api.GitLabApi;

public class ApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public GenericObjectPool<GitLabApi> contextPools;

	@PostConstruct
	  public void init() {
	      ApiPoolFactory factory = new ApiPoolFactory();
	      //新建一个对象池,传入对象工厂和配置
	      contextPools = new GenericObjectPool<>(factory);
	  }

}
