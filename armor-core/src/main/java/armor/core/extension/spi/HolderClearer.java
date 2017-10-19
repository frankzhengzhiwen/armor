/**
 * Copyright (c) 2017, armor All Rights Reserved. 
 */  
 
package armor.core.extension.spi;

import java.util.List;

import armor.core.tool.extension.ArmorExtensionLoader;

/**
 * 清理所有保存的线程栈数据
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月5日
 */
public interface HolderClearer {
	
	void clear();

	/**
	 * 清除所有线程
	 */
	static void clearAll(){
		List<HolderClearer> clearers = ArmorExtensionLoader
						.getInstance(HolderClearer.class).get();
		for(HolderClearer clearer : clearers) {
			clearer.clear();
		}
	}
}
