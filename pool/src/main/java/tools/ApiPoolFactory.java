package tools;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.gitlab4j.api.GitLabApi;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiPoolFactory implements PooledObjectFactory<GitLabApi>{

	@Override
	public void activateObject(PooledObject<GitLabApi> p) throws Exception {
	}

	@Override
	public void destroyObject(PooledObject<GitLabApi> p) throws Exception {
		log.info("销毁对象");
	}

	@Override
	public PooledObject<GitLabApi> makeObject() throws Exception {
		return new DefaultPooledObject<GitLabApi>(new GitLabApi(null, null));
	}

	@Override
	public void passivateObject(PooledObject<GitLabApi> p) throws Exception {
	}

	@Override
	public boolean validateObject(PooledObject<GitLabApi> p) {
		return true;
	}

}
