package org.cts.microservice.framework.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	/*public static void main(String[] args) throws Exception {
		pack("D:\\upload\\tmpFiles", "D:\\upload\\tmpFiles.zip");
	}*/

	public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
		Path p = Files.createFile(Paths.get(zipFilePath));

		ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p));
		try {
			Path pp = Paths.get(sourceDirPath);
			Files.walk(pp).filter(path -> !Files.isDirectory(path)).forEach(path -> {
				String sp = path.toAbsolutePath().toString().replace(pp.toAbsolutePath().toString(), "")
						.replace(path.getFileName().toString(), "");
				ZipEntry zipEntry = new ZipEntry(sp + "/" + path.getFileName().toString());
				try {
					zs.putNextEntry(zipEntry);
					zs.write(Files.readAllBytes(path));
					zs.closeEntry();
				} catch (Exception e) {
					System.err.println(e);
				}
			});
		} finally {
			zs.close();
		}
	}

}