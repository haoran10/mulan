package wang.conge.springdemo.web.exception;

import wang.conge.springdemo.web.dto.CommonError;

/**
 * 
 * @author conge.wang
 *
 * 2015年7月31日
 */

public class ErrorCodeException extends Exception{
	private static final long serialVersionUID = -3699996786795632409L;
	
	public ErrorCodeException(){};
	
	public ErrorCodeException(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public ErrorCodeException(CommonError error){
		this.code = error.getCode();
		this.msg = error.getMsg();
	}
	
	public ErrorCodeException(CommonError error,String msg){
		this.code = error.getCode();
		this.msg = msg;
	}
	
	private int code;
	private String msg;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
