/**

 *

 *
 * 
 */

package net.geedge.common.exception;

import net.geedge.common.utils.RCode;

/**
 * 自定义异常
 *
 * @author Mark sunlightcs@gmail.com
 */
public class CNException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = RCode.ERROR.getCode();
    
    public CNException(RCode rCode) {
    	this.code = rCode.getCode();
    	this.msg = rCode.getMsg();
    	
    }
    
    public CNException(String msg) {
    	super(msg);
		this.msg = msg;
	}
	
	public CNException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public CNException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public CNException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
