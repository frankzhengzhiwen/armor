/**
 * Copyright (c) 2017, armor All Rights Reserved. 
 */  
 
package armor.codebinder.extension.spi;

import armor.codebinder.model.BusinessInfo;

/**
 * Web请求拦截器：
 * 失败自行抛出异常
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月21日
 */
public interface ApiPreHandler {

	/**
	 * @param businessInfo
	 */
	void preHandle(BusinessInfo businessInfo);
}
