/**
 * Copyright (c) 2017, armor All Rights Reserved. 
 */  
 
package armor.orm.extension.support;

import com.github.pagehelper.PageHelper;

import armor.core.extension.spi.HolderClearer;

/**
 * <p>分页插件清除本地变量</p>
 * 可能存在生成但不使用，造成的内存泄漏 
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月5日
 */
public class PageHelperClearer implements HolderClearer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		PageHelper.clearPage();
	}

}
