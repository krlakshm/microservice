/**
 * 
 */
package org.cts.microservice.framework.builder;

import org.cts.microservice.framework.entity.BuilderRequest;

import com.cts.mavenbuilder.jaxbclasses.Model;

/**
 * @author 153780
 *
 */
public interface RestAPIClassMavenGeneratorI {
	
	/**
	 * 
	 * @param builderReqeust
	 * @return
	 */
	Model createMavenRestProject(BuilderRequest builderReqeust);
	
	/**
	 * 
	 * @param builderRequest
	 * @return
	 */
	Model fillPOMFile(BuilderRequest builderRequest,Model model);
	
	/**
	 * 
	 * @param projectName
	 */
	void executeProject(String projectName);
	
	/**
	 * 
	 * @param projectName
	 */
	void runJavaDocCommand(String projectName);
}
