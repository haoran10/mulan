package wang.conge.springdemo.core.dto;

/**
 * 
 * @author conge.wang
 *
 * 2015年7月31日
 */

public class BaseDto<T> {
	private int code;
	private String msg;
	private T data;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
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
