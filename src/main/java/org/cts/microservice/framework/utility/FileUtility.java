package org.cts.microservice.framework.utility;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileUtility {
	
	/**
	 * Upload multiple file using Spring Controller
	 */
	public static String uploadFileHandler(MultipartFile file) {
		
		String filePath;
		if (file != null && !file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				//String rootPath = System.getProperty("catalina.home");
				String rootPath = "D:/upload/";
				//File dir = new File(rootPath);
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				//File serverFile = new File(dir.getAbsolutePath());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location="
						+ serverFile.getAbsolutePath());
				filePath = serverFile.getAbsolutePath();
				return filePath;
			} catch (Exception e) {
				//isFileUploaded = false;
				//return serverFile.getAbsolutePath();
				return null;
			}
		} else {
			//isFileUploaded = false;
			return null;
		}
	}
	
	
	/**
	 * Upload multiple file using Spring Controller
	 */
	public static String uploadMultipleFileHandler(MultipartFile[] files) {


		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			//String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location="
						+ serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + file.getOriginalFilename()+ "";
			} catch (Exception e) {
				return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
			}
		}
		return message;
	}


}
