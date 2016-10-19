package org.cts.microservice.framework.utility;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.cts.microservice.framework.entity.PortMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author 585915
 *
 */
public class JarUtil {

	/*public static void main(String args[]) throws IOException, ClassNotFoundException {

		Map<String, List<PortMethod>> portDetailsMap = new HashMap<String, List<PortMethod>>();
		String jarname = "jars/vipuserservices_stubs-1.0.jar";
		portDetailsMap = getPortDetailsMap(jarname);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(portDetailsMap);

		System.out.println("Here is your output as Json\n" + jsonInString);

	}*/

	public static Map<String, List<PortMethod>> getPortDetailsMap(String jarname)
			throws IOException, ClassNotFoundException {

		Map<String, List<PortMethod>> portDetailsMap = new HashMap<String, List<PortMethod>>();

		List<String> portClassList = getListOfPortClass(jarname);

		for (String classname : portClassList) {
			List<PortMethod> PortMethodList = new ArrayList<PortMethod>();
			List<Method> methodNames = getListOfPortMethod(classname);

			for (Method method : methodNames) {
				PortMethod portMethod = new PortMethod();
				List<String> parameterNames = getParameterNames(method);

				portMethod.setOperationName(method.getName());
				portMethod.setReturnType(method.getReturnType().getName());
				portMethod.setRequestParams(parameterNames);

				PortMethodList.add(portMethod);
			}
			portDetailsMap.put(classname, PortMethodList);
		}

		return portDetailsMap;
	}

	public static List<String> getParameterNames(Method method) {
		Parameter[] parameters = method.getParameters();
		List<String> parameterNames = new ArrayList<>();

		for (Parameter parameter : parameters) {
			Class<?> parameterType = parameter.getType();
			String parameterName = parameterType.getName();
			parameterNames.add(parameterName);
		}

		return parameterNames;
	}

	public static List<Method> getListOfPortMethod(String classname) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(classname);
		List<Method> methodNamesList = new ArrayList<Method>();
		Method[] methods2 = clazz.getDeclaredMethods();
		methodNamesList = new ArrayList<Method>(Arrays.asList(methods2));
		return methodNamesList;

	}

	public static List<String> getListOfPortClass(String jarname) throws IOException {
		List<String> classNamesList = new ArrayList<String>();

		InputStream inputstream = JarUtil.class.getClassLoader().getResourceAsStream(jarname);
		ZipInputStream zip = new ZipInputStream(inputstream);

		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
			if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
				String className = entry.getName().replace('/', '.');
				String classNameWithoutSuffix = className.substring(0, className.length() - ".class".length());
				if (classNameWithoutSuffix.endsWith("Port"))
					classNamesList.add(classNameWithoutSuffix);
			}
		}

		return classNamesList;
	}

}
