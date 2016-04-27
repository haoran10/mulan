package wang.conge.springdemo.core.dto;

/**
 * 
 * @author conge.wang
 *
 * 2015年7月31日
 */

public class DtoUtil {
	//dto
	public static <T> BaseDto<T> ok(){
		return dto(CommonError.OK, null);
	}
	
	public static <T> BaseDto<T> ok(T result){
		return dto(CommonError.OK, result);
	}
	
	
	public static <T> BaseDto<T> dto(CommonError error,T t){
		if(error==null){
			error = CommonError.SYS_ERROR;
		}
		
		BaseDto<T> dto = new BaseDto<T>();
		dto.setCode(error.getCode());
		dto.setMsg(error.getMsg());
		dto.setData(t);
		
		return dto;
	}
	
	public static <T> BaseDto<T> dto(CommonError error){
		if(error==null){
			error = CommonError.SYS_ERROR;
		}
		
		BaseDto<T> dto = new BaseDto<T>();
		dto.setCode(error.getCode());
		dto.setMsg(error.getMsg());
		
		return dto;
	}
	
	public static <T> BaseDto<T> dto(CommonError error,String msg){
		if(error==null){
			error = CommonError.SYS_ERROR;
		}
		
		BaseDto<T> dto = new BaseDto<T>();
		dto.setCode(error.getCode());
		dto.setMsg(msg);
		
		return dto;
	}
}
