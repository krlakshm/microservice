/**
 * 
 */
package org.cts.microservice.framework.builder.impl;

import org.cts.microservice.framework.builder.RestAPIClassMavenGeneratorI;
import org.cts.microservice.framework.entity.BuilderRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.mavenbuilder.MavenBuilder;
import com.cts.mavenbuilder.jaxbclasses.Exclusion;
import com.cts.mavenbuilder.jaxbclasses.Model;

/**
 * @author 153780
 *
 */
public class RestAPIClassMavenGenerarorImpl implements RestAPIClassMavenGeneratorI {
	
	@Autowired
	MavenBuilder mb;
	/* (non-Javadoc)
	 * @see org.cts.microservice.framework.builder.RestAPIClassMavenGeneratorI#createMavenRestProject(org.cts.microservice.framework.entity.BuilderRequest)
	 */
	@Override
	public Model createMavenRestProject(BuilderRequest builderRequest) {
		Model model = mb.createJavaProject(builderRequest.getArtifact(), builderRequest.getGroup(),
				builderRequest.getArtifact(), "");
		return model;
	}

	/* (non-Javadoc)
	 * @see org.cts.microservice.framework.builder.RestAPIClassMavenGeneratorI#fillPOMFile(org.cts.microservice.framework.entity.BuilderRequest)
	 */
	@Override
	public Model fillPOMFile(BuilderRequest builderRequest, Model model) {
		if(model != null) {
			model = mb.addParent(model, "org.springframework.boot", "spring-boot-starter-parent", "1.4.0.RELEASE");
			model = mb.addDependencytoPom(model, "org.springframework.boot", "spring-boot-starter-jersey", "", null, null, null, null);
			model = mb.addDependencytoPom(model, "org.springframework.boot", "spring-boot-starter-web", "", null, null, null, null);
			model = mb.addDependencytoPom(model, "org.springframework.boot", "spring-boot-starter-test", "", null, null, null, null);
			model = mb.addDependencytoPom(model,builderRequest.getGroup()+".soapstub", builderRequest.getArtifact()+"SOAPStub", "1.0", null, null, null, null);
			model = mb.addDependencytoPom(model, "net.sf.dozer", "dozer", "5.4.0", null, null, null, null);
			
			//add one method for adding dependency with Exclusion
			
			/*model = mb.addPluginForWsdlGeneration(model, "org.apache.cxf", "cxf-codegen-plugin", "${cxf.version}", "generate-sources",
					"generate-sources", "wsdl2java", wsdlFilePath, "");*/
			model = mb.addPropertiesToModel(model,"REST");
			model = mb.addPlugin(model, "org.springframework.boot", "spring-boot-maven-plugin", "", "","", "", "", "");
			model = mb.addPlugin(model, "org.apache.maven.plugins", "maven-javadoc-plugin", "2.10.4", "","", "", "", "");
			
		}

		return model;
	}

	/* (non-Javadoc)
	 * @see org.cts.microservice.framework.builder.RestAPIClassMavenGeneratorI#executeProject(java.lang.String)
	 */
	@Override
	public void executeProject(String projectName) {
		mb.executeProject(projectName);

	}

	/* (non-Javadoc)
	 * @see org.cts.microservice.framework.builder.RestAPIClassMavenGeneratorI#runJavaDocCommand(java.lang.String)
	 */
	@Override
	public void runJavaDocCommand(String projectName) {
		mb.executeJavaDoc(projectName);

	}

}
