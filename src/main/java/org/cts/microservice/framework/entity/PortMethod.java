package org.cts.microservice.framework.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 585915
 *
 */
public class PortMethod {

	private String operationName;
	private String returnType;
	private List<String> requestParams = new ArrayList<String>();

	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @param operationName
	 *            the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType
	 *            the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the requestParams
	 */
	public List<String> getRequestParams() {
		return requestParams;
	}

	/**
	 * @param requestParams
	 *            the requestParams to set
	 */
	public void setRequestParams(List<String> requestParams) {
		this.requestParams = requestParams;
	}

}
