package com.teai.encryptstarter.result;

import java.io.Serializable;

/**
 * 返回结果封装
 */
public class ResultInfo implements Serializable {

	private static final long serialVersionUID = -3227305665373461505L;


	//状态码
    protected Integer code;
    //消息体
	protected String message;
    //结果
	protected Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
