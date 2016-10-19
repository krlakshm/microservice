/**
 * 
 */
package org.cts.microservice.framework.builder.impl;

import org.cts.microservice.framework.entity.BuilderRequest;
import org.cts.microservice.framework.utility.FileUtility;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * This is the orchestration class for creation of the rest project and its
 * related Spring boot framed classes for wrapping soap services
 *
 */
public class RestAPIGeneratorForSOAP extends SpringBootApplicationGenerator {

	private MultipartFile file;
	
	public RestAPIGeneratorForSOAP(MultipartFile file) {
		this.file = file;
	}
	
	@Override
	public void generate(BuilderRequest builderRequest) {
		String wsdlFilePath = FileUtility.uploadFileHandler(file);
		// TODO Auto-generated method stub
		/*
		 * 1) Store the Stub jar in server location-sv
		 * 2) Install the stub jar in maven repo-sv
		 * 3) Create Maven project for the REST Wrapper-sv
		 * 4) Fill the POM file with Sprng Boot and SOAP related dependencies and plugins-sv
		 * 5) Read jar and populate the MAP -ss/nmd */
		 
		/* 6) Read Map to convert Jaxb Request and Response to Plain pojos-ss/nd
		 * 7) Read Map to create REST classes (using the MAP and the Plan pojos-lb
		 * 8) Marshal the POM-sv
		 * 9) Create a zip version of the REST Project and send back to the client-sv/lb
		 */
		//RestAPIClassMavenGenerarorImpl rest
	}


	
}
