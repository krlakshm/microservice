package org.cts.microservice.framework.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cts.microservice.framework.builder.impl.RestAPIGeneratorForDB;
import org.cts.microservice.framework.builder.impl.RestAPIGeneratorForFile;
import org.cts.microservice.framework.builder.impl.RestAPIGeneratorForMQ;
import org.cts.microservice.framework.builder.impl.RestAPIGeneratorForSOAP;
import org.cts.microservice.framework.builder.impl.SOAPStubProjectGenerator;
import org.cts.microservice.framework.builder.impl.SpringBootApplicationGenerator;
import org.cts.microservice.framework.entity.BuilderRequest;
import org.cts.microservice.framework.utility.FileUtility;
import org.cts.microservice.framework.utility.ZipUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MicroServiceController {

	SpringBootApplicationGenerator generator;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homepage() {
		return "index";
	}

	@RequestMapping(value = "/stubs", method = RequestMethod.GET)
	public String stubpage() {
		return "stubs";
	}

	private static final int BUFFER_SIZE = 4096;

	/**
	 * Path of the file to be downloaded, relative to application's directory
	 */
	private String filePath = "/output/SooperMachi.zip";

	@RequestMapping(value = "/createrestapi", method = RequestMethod.POST)
	public void createRestApi(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("createapiparm") String reqBean) {
		// How do we get the serviceType??
		ObjectMapper objMapper = new ObjectMapper();
		BuilderRequest objBuilderRequest = new BuilderRequest();
		try {
			objBuilderRequest = objMapper.readValue(new StringReader(reqBean), BuilderRequest.class);
			switch(objBuilderRequest.getServicetype()) {
				case "SOAP":
					generator = new RestAPIGeneratorForSOAP(file);
					generator.generate(objBuilderRequest);
					break;
				case "DB":
					generator = new RestAPIGeneratorForDB();
					generator.generate(objBuilderRequest);
					break;
				case "FILE":
					generator = new RestAPIGeneratorForFile();
					generator.generate(objBuilderRequest);
					break;
				case "MQ":
					generator = new RestAPIGeneratorForMQ();
					generator.generate(objBuilderRequest);
					break;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@RequestMapping(value = "/createstubs", method = RequestMethod.POST)
	@ResponseBody
	public void createStubs(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam("CreateStubsParam") String reqBean, HttpServletRequest request,
			HttpServletResponse response) {
		boolean isStubsCreated = true;
		try {

			ObjectMapper objMapper = new ObjectMapper();
			BuilderRequest objBuilderRequest = objMapper.readValue(new StringReader(reqBean), BuilderRequest.class);

			String wsdlFilePath = FileUtility.uploadFileHandler(file);
			FileUtility.uploadFileHandler(file);
			FileUtility.uploadFileHandler(file1);

			SOAPStubProjectGenerator stubGenerator = new SOAPStubProjectGenerator();
			isStubsCreated = stubGenerator.stubGenerator(objBuilderRequest, wsdlFilePath);

			String basedir = System.getProperty("user.dir");
			ZipUtils.pack(basedir+"/output/"+objBuilderRequest.getArtifact(),basedir+"/output/"+objBuilderRequest.getArtifact()+".zip");

			// get absolute path of the application
			ServletContext context = request.getServletContext();
			String appPath = context.getRealPath("");
			System.out.println("appPath = " + appPath);

			// construct the complete absolute path of the file
			String fullPath = basedir + "/output/"+objBuilderRequest.getArtifact()+".zip";
			File downloadFile = new File(fullPath);
			FileInputStream inputStream = new FileInputStream(downloadFile);

			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			System.out.println("MIME type: " + mimeType);

			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			isStubsCreated = false;
		}
		// return isStubsCreated;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void doDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// get absolute path of the application
		ServletContext context = request.getServletContext();
		// String appPath = context.getRealPath("");
		String basedir = System.getProperty("user.dir");
		// System.out.println("appPath = " + appPath);

		// construct the complete absolute path of the file
		String fullPath = basedir + filePath;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		System.out.println("MIME type: " + mimeType);

		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();

	}

}