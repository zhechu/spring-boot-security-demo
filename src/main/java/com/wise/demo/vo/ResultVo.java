package com.wise.demo.vo;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一 Restful 数据返回格式
 * @author lingyuwang
 *
 * @param <T>
 */
@Setter
@Getter
public class ResultVo<T> {

	/**
	 * 状态码值
	 */
	private int code;
	
	/**
	 * 结果说明
	 */
	private String message;

	/**
	 * 结果值
	 */
	private T data;

	public ResultVo() {
		this.setCode(HttpStatus.OK.value());
	}

	public ResultVo(int code) {
		this.setCode(code);
	}
	
	public ResultVo(int code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}

	public ResultVo(int code, String message, T data) {
		this.setCode(code);
		this.setMessage(message);
		this.setData(data);
	}

	/**
	 * 构建一个成功状态的结果
	 * 
	 * @return
	 */
	public static <T> ResultVo<T> buildSuccessResult() {
		return new ResultVo<T>();
	}

	public static <T> ResultVo<T> buildExceptionResult(String message) {
		return new ResultVo<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}
	
	/**
	 * 构建一个包含数据的成功结果
	 * 
	 * @param data
	 *            数据
	 * @return
	 */
	public static <T> ResultVo<T> buildSuccessResult(T data) {
		return new ResultVo<T>(HttpStatus.OK.value(), null, data);
	}
	
	/**
	 * 从目标jsonResult构建一个新的jsonResult,不含data数据
	 * 
	 * @param jsonResult
	 * @return
	 */
	public static <T> ResultVo<T> buildResultWithOutData(ResultVo<?> jsonResult) {
		return new ResultVo<T>(jsonResult.getCode(), jsonResult.getMessage(), null);
	}
	
}
