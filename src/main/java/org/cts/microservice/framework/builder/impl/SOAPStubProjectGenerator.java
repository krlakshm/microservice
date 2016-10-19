package org.cts.microservice.framework.builder.impl;

import java.util.HashMap;
import java.util.Map;

import org.cts.microservice.framework.entity.BuilderRequest;

import com.cts.mavenbuilder.MavenBuilder;
import com.cts.mavenbuilder.jaxbclasses.Model;
import com.cts.mavenbuilder.util.MavenBuilderUtility;

public class SOAPStubProjectGenerator {

	public boolean stubGenerator(BuilderRequest builderRequest, String wsdlFilePath) throws Exception {
		MavenBuilder mb = new MavenBuilder();
		Model model = mb.createJavaProject(builderRequest.getArtifact(), builderRequest.getGroup(),
				builderRequest.getArtifact(), "");
		model = mb.addDependencytoPom(model, "com.sun.xml.bind", "jaxb-core", "2.2.11", null, null, null, null);
		
		model = mb.addPlugin(model, "org.apache.cxf", "cxf-codegen-plugin", "${cxf.version}", "generate-sources",
				"generate-sources", "wsdl2java", wsdlFilePath, "");
		model = mb.addPropertiesToModel(model,"SOAP");

		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.clear();
		arguments.put("0", builderRequest.getArtifact());
		String filePath = MavenBuilderUtility.format("/output/${0}/pom.xml", arguments);
		String basedir = System.getProperty("user.dir");

		MavenBuilderUtility.marshalModelToPom(model, basedir + filePath);

		mb.executeProject(builderRequest.getArtifact());
		mb.installJarToLocalRepository(builderRequest.getGroup(), builderRequest.getArtifact(), "1.0");

		return true;

	}
}
