package org.cts.microservice.framework.entity;

import org.springframework.web.multipart.MultipartFile;

public class BuilderRequest {
	
	private String group;
	private String artifact;
	private String json;
	private String wsdlurl;
	private String apiname;
	private String restapiurl;
	private String headervalues;
	private String jsonreq;
	private String jsonres;
	private String basepackage;
	private String servicetype;
	private String methodtype;
	private String version;
	private String projecttype;
	
	
		/**
	 * @return the projecttype
	 */
	public String getProjecttype() {
		return projecttype;
	}
	/**
	 * @param projecttype the projecttype to set
	 */
	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}
		/**
	 * @return the basepackage
	 */
	public String getBasepackage() {
		return basepackage;
	}
	/**
	 * @param basepackage the basepackage to set
	 */
	public void setBasepackage(String basepackage) {
		this.basepackage = basepackage;
	}
	/**
	 * @return the apiname
	 */
	public String getApiname() {
		return apiname;
	}
	/**
	 * @param apiname the apiname to set
	 */
	public void setApiname(String apiname) {
		this.apiname = apiname;
	}
	/**
	 * @return the restapiurl
	 */
	public String getRestapiurl() {
		return restapiurl;
	}
	/**
	 * @param restapiurl the restapiurl to set
	 */
	public void setRestapiurl(String restapiurl) {
		this.restapiurl = restapiurl;
	}
	/**
	 * @return the headervalues
	 */
	public String getHeadervalues() {
		return headervalues;
	}
	/**
	 * @param headervalues the headervalues to set
	 */
	public void setHeadervalues(String headervalues) {
		this.headervalues = headervalues;
	}
	/**
	 * @return the jsonreq
	 */
	public String getJsonreq() {
		return jsonreq;
	}
	/**
	 * @param jsonreq the jsonreq to set
	 */
	public void setJsonreq(String jsonreq) {
		this.jsonreq = jsonreq;
	}
	/**
	 * @return the jsonres
	 */
	public String getJsonres() {
		return jsonres;
	}
	/**
	 * @param jsonres the jsonres to set
	 */
	public void setJsonres(String jsonres) {
		this.jsonres = jsonres;
	}
	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * @return the artifact
	 */
	public String getArtifact() {
		return artifact;
	}
	/**
	 * @param artifact the artifact to set
	 */
	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}
	/**
	 * @return the json
	 */
	public String getJson() {
		return json;
	}
	/**
	 * @param json the json to set
	 */
	public void setJson(String json) {
		this.json = json;
	}
	
	public String getWsdlurl() {
		return wsdlurl;
	}
	public void setWsdlurl(String wsdlurl) {
		this.wsdlurl = wsdlurl;
	}
	/**
	 * @return the methodtype
	 */
	public String getMethodtype() {
		return methodtype;
	}
	/**
	 * @param methodtype the methodtype to set
	 */
	public void setMethodtype(String methodtype) {
		this.methodtype = methodtype;
	}
	
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the servicetype
	 */
	public String getServicetype() {
		return servicetype;
	}
	/**
	 * @param servicetype the servicetype to set
	 */
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BuilderRequest [group=" + group + ", artifact=" + artifact + ", json=" + json + ", wsdlurl=" + wsdlurl
				+ ", apiname=" + apiname + ", restapiurl=" + restapiurl + ", headervalues=" + headervalues
				+ ", jsonreq=" + jsonreq + ", jsonres=" + jsonres + ", basepackage=" + basepackage + ", servicetype="
				+ servicetype + ", methodtype=" + methodtype + ", version=" + version + "]";
	}
}
