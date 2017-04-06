package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class testLog {
	
	@Test
	public void test(){
		Log log =LogFactory.getLog(getClass());
		try {
			int i=1/0;
		} catch (Exception e) {
			log.fatal("致命错误"+e.getMessage());
		}

		/*log.debug("调试级别");
		log.info("信息级别");
		log.warn("warn 级别");
		log.error("错误级别");
		log.fatal("致命级别");*/
	}
}
