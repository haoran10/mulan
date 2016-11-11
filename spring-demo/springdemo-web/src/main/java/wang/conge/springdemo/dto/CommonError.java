package wang.conge.springdemo.dto;

/**
 * 错误状态枚举
 * @author conge.wang
 *
 * 2015年7月31日
 */
public enum CommonError {
	/**通用状态*/
	OK(200,"OK"),
	
	PARAM_ERROR(4000,"参数格式错误"),
	
	SIGN_ERROR(4001,"SIGN ERR"),
	
	TOKEN_ERROR(4002,"TOKEN ERR"),
	
	SYS_ERROR(5000,"一个神奇的错误"),
	
	
	
	;
	  /** code状态值 */
    private int code;

    /** 信息描述 */
    private String msg;

    /**
     * <p>私有构造函数，枚举不允许外部调用构造新的枚举值。</p>
     * 
     * @param code               键值
     * @param msg                描述值
     */
    private CommonError(int code, String msg) {
        this.code = code;
        this.msg = msg;
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


	/**
     * <p>工厂方法，从指定的code值获取对应枚举</p>
     */
    public static final CommonError getFromCode(int code) {
        for (CommonError e:CommonError.values()) {
            if (e.getCode()==code) {
                return e;
            }
        }
        return null;
    }

}