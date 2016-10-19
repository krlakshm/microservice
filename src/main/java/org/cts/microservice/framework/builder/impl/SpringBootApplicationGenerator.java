package org.cts.microservice.framework.builder.impl;

import java.io.IOException;

import org.cts.microservice.framework.entity.BuilderRequest;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.NoopAnnotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

/**
 * @author 153780
 *
 */
public abstract class SpringBootApplicationGenerator {
	
	JCodeModel codeModel;
	
	public abstract void generate(BuilderRequest builderRequest);
	
	/**
	 * 
	 * @param builderRequest
	 */
	public void createSpringBootApplication(BuilderRequest builderRequest) {
		 try {
			JDefinedClass springApplication = codeModel._class(JMod.PUBLIC, builderRequest.getBasepackage()+".main."+builderRequest.getArtifact()+"Application", ClassType.CLASS);
			
			//Add spring boot annotations
			springApplication.annotate(codeModel.ref("org.springframework.context.annotation.Configuration"));
			springApplication.annotate(codeModel.ref("org.springframework.boot.autoconfigure.EnableAutoConfiguration"));
			springApplication.annotate(codeModel.ref("org.springframework.context.annotation.ComponentScan"));
			
			//add psvm
			JMethod mainMethod = springApplication.method(JMod.PUBLIC|JMod.STATIC, void.class, "main");
			mainMethod.param(String[].class, "args");
			
			//add content to the main method
			JBlock block = mainMethod.body();
			block.directStatement("SpringApplication.run("+builderRequest.getArtifact()+"Application.class, args);");
			
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	/**
	 * 
	 * @param rootPackage
	 * @param json
	 * @param pojoName
	 * @param isDTO
	 * @throws IOException
	 */
	public void createPOJO(String rootPackage,String json, String pojoName, boolean isDTO) throws IOException {
		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() { // set config option by overriding method
				return true;
			}
            public SourceType getSourceType(){
                return SourceType.JSON;
            }
		};

		SchemaMapper mapper = null;
		
		String packagename = null;
		String pojoNameTemp = null;
		
		if(isDTO) {
			packagename = rootPackage+ ".model.dtos";
			pojoNameTemp = pojoName + "DTO";
			mapper = new SchemaMapper(new RuleFactory(config, new NoopAnnotator(), new SchemaStore()), new SchemaGenerator());
		}
		else {
			packagename = rootPackage+ "." + "model.beans";
			pojoNameTemp = pojoName;
			mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(), new SchemaStore()), new SchemaGenerator());
		}
		mapper.generate(codeModel, pojoNameTemp, packagename, json);

	}
	
}
