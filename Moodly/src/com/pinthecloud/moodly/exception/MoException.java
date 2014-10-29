package com.pinthecloud.moodly.exception;

import com.pinthecloud.moodly.fragment.MoFragment;

public class MoException extends RuntimeException {

	private static final long serialVersionUID = -5944663372661859514L;

	private MoException.TYPE type;
	private MoFragment from;
	private String methodName;
	private Object parameter;

	public MoException(String string) {
		super(string);
	}

	public MoException(TYPE type) {
		this.type = type;
		this.from = null;
	}

	public MoException(MoFragment from, String methodName, TYPE type) {
		this.from = from;
		this.type = type;
		this.methodName = methodName;
	}

	public MoException(MoFragment from, String methodName, TYPE type, Object parameter) {
		this.from = from;
		this.type = type;
		this.methodName = methodName;
		this.parameter = parameter;
	}

	public TYPE getType() {
		return type;
	}

	public MoFragment fromWho() {
		return from;
	}

	public String getMethodName() {
		return methodName;
	}

	public Object getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		if (super.getMessage() != null) { 
			return "{ message : " + super.getMessage() + " }";
		}
		return "{ type : " + type + "," +
		" from : " + from.getClass().getSimpleName()  + "," +
		" method : " + methodName + " }";
	}

	public enum TYPE {
		INTERNET_NOT_CONNECTED,
		SERVER_ERROR,
		BLOB_STORAGE_ERROR,
		DUPLICATED_NICK_NAME
	}
}
