package org.cts.microservice.framework.builder.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


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
import org.springframework.stereotype.Component;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

//@Component("MicroServiceBuilder")
public class MicroServiceBuilderImpl  {

	JCodeModel codeModel;
	String projectName;
	String serviceName;
	String pojoName;
	String rootPackage;
	String jsonFilePath;
	String relativePath;
	String keyField;
	BuilderRequest builderRequest;
	
	/**
	 * 
	 * @param projectName
	 * @param serviceName
	 * @param rootPackage
	 * @param pojoName
	 * @param jsonFilePath
	 * @param relativePath
	 */
	public MicroServiceBuilderImpl(String projectName, String serviceName, String rootPackage, String pojoName, String jsonFilePath, String relativePath,String keyField) {
		codeModel = new JCodeModel();
		//codeModel._package("src/main/java");
		this.projectName = projectName;
		this.serviceName = serviceName;
		this.rootPackage = rootPackage;
		this.pojoName = pojoName;
		this.jsonFilePath = jsonFilePath;
		this.relativePath = relativePath;
		this.keyField = keyField;
	}
	
	public MicroServiceBuilderImpl(BuilderRequest builderRequest) {
		if(builderRequest != null) {
			this.builderRequest = builderRequest;
		}
	}
	
	/**
	 * main build service
	 */
	public void buildMicroservice() {
		URL source;
		
		/*
		 * Try reading the json file
		 */
		try {
			source = new URL(jsonFilePath);
			
			// now call the different creation services.
			createSpringBootApplication(projectName, rootPackage);
			//created implementation
			createJSONOrPlanPOJO( source, pojoName, rootPackage, false);
			//created implementation
			createJSONOrPlanPOJO(source, pojoName, rootPackage, true);
			//createRepositoryInterface( serviceName, rootPackage,pojoName,keyField);
			createServiceInterface( serviceName, rootPackage,pojoName,keyField);
			createServiceImplementation(serviceName, rootPackage);
			createSpringController(serviceName, relativePath, rootPackage);
			
			codeModel.build(new File("output/src/main/java"));
			
		} catch(MalformedURLException malformed) {
			System.out.println("Cannot find the json file in the file given");
		} catch(Exception excep) {
			System.out.println("exception in reading the json file" + excep);
		}
		
		
	}
	
	public void createSpringBootApplication( String projectName, String rootPackage) {
		 try {
			JDefinedClass springApplication = codeModel._class(JMod.PUBLIC, rootPackage+".main."+projectName+"Application", ClassType.CLASS);
			
			//Add spring boot annotations
			springApplication.annotate(codeModel.ref("org.springframework.context.annotation.Configuration"));
			springApplication.annotate(codeModel.ref("org.springframework.boot.autoconfigure.EnableAutoConfiguration"));
			springApplication.annotate(codeModel.ref("org.springframework.context.annotation.ComponentScan"));
			
			//add psvm
			JMethod mainMethod = springApplication.method(JMod.PUBLIC|JMod.STATIC, void.class, "main");
			mainMethod.param(String[].class, "args");
			
			//add content to the main method
			JBlock block = mainMethod.body();
			block.directStatement("SpringApplication.run("+projectName+"Application.class, args);");
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	public void createJSONOrPlanPOJO( URL source, String pojoName, String rootPackage,
			boolean isDTO) throws Exception {

		//source = new URL("file:///D:/schema.json");
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
		mapper.generate(codeModel, pojoNameTemp, packagename, source);
		
	}

	public void createRepositoryInterface( String serviceName, String rootPackage, String pojoName, String keyPropertyName) {
		 try {
			 
			JDefinedClass repoInterface = codeModel._class(JMod.PUBLIC, rootPackage+".repositories."+serviceName+"Repository", ClassType.INTERFACE);
			
			JDefinedClass pojoClass = codeModel._getClass(rootPackage+".model.beans."+ pojoName);
			
			Map<String,JFieldVar> mapOfFields = pojoClass.fields();
			JFieldVar idVar = (JFieldVar)mapOfFields.get(keyPropertyName);
			
			JMethod deleteMethod = repoInterface.method(0, void.class, "delete");
			deleteMethod.param(pojoClass,pojoName);
			
			repoInterface.method(0, List.class, "findAll");
			
			JMethod findByIdMethod = repoInterface.method(0, pojoClass, "findOne");
			findByIdMethod.param(idVar.type(), keyPropertyName);

			JMethod saveMethod = repoInterface.method(0, pojoClass, "save");
			saveMethod.param(pojoClass,pojoName);

		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		
	}

	public void createServiceInterface( String serviceName, String rootPackage, String pojoName, String keyPropertyName) {
		 try {
			JDefinedClass serviceInterface = codeModel._class(JMod.PUBLIC, rootPackage+".services."+serviceName+"Service", ClassType.INTERFACE);
			JDefinedClass dtoClass = codeModel._getClass(rootPackage+".model.dtos."+ pojoName+"DTO");
			
			JMethod createMethod = serviceInterface.method(0, dtoClass, "create");
			createMethod.param(dtoClass, "dtoObject");
			
			Map<String,JFieldVar> mapOfFields = dtoClass.fields();
			JFieldVar idVar = (JFieldVar)mapOfFields.get(keyPropertyName);
			
			JMethod deleteMethod = serviceInterface.method(0, dtoClass, "delete");
			deleteMethod.param(idVar.type(), keyPropertyName);
			
			serviceInterface.method(0, List.class, "findAll");
			
			JMethod findByIdMethod = serviceInterface.method(0, dtoClass, "findById");
			findByIdMethod.param(idVar.type(), keyPropertyName);

			JMethod updateMethod = serviceInterface.method(0, dtoClass, "update");
			updateMethod.param(dtoClass, "dtoObject");


		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	public void createServiceImplementation( String serviceName, String rootPackage) {
		// TODO Auto-generated method stub
		
	}

	public void createSpringController( String serviceName, String relativePath,
			String rootPackage) {
		// TODO Auto-generated method stub
		
	}


	
}
