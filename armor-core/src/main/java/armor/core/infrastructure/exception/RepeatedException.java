/**
 * Copyright (c) 2017, armor All Rights Reserved. 
 */  
 
package armor.core.infrastructure.exception;

import armor.core.infrastructure.message.Message;

/**
 * 重复操作异常
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月31日
 */
public class RepeatedException extends BaseException {

	private static final long serialVersionUID = -968914943434322019L;

	/**
	 * Creates a new instance of RepeatedException
	 * 
	 * @param message
	 * @param cause
	 */
	public RepeatedException(Throwable cause) {
		super(cause);
		withCode(Message.REPEATED_ERROR);
	}

	/**
	 * Creates a new instance of RepeatedException
	 * 
	 * @param message
	 */
	public RepeatedException() {
		super();
		withCode(Message.REPEATED_ERROR);
	}

}
