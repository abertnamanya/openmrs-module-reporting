package org.openmrs.module.reporting.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.junit.Ignore;
import org.openmrs.util.OpenmrsUtil;

@Ignore
public class TestUtil {

	public static final String TEST_DATASETS_PROPERTIES_FILE = "test-datasets.properties";
	
	public String loadXmlFromFile(String filename) throws Exception {
		InputStream fileInInputStreamFormat = null;
		
		// try to load the file if its a straight up path to the file or
		// if its a classpath path to the file
		if (new File(filename).exists()) {
			fileInInputStreamFormat = new FileInputStream(filename);
		} else {
			fileInInputStreamFormat = getClass().getClassLoader().getResourceAsStream(filename);
			if (fileInInputStreamFormat == null)
				throw new FileNotFoundException("Unable to find '" + filename + "' in the classpath");
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(fileInInputStreamFormat, Charset.forName("UTF-8")));
		while (true) {
			String line = r.readLine();
			if (line == null)
				break;
			sb.append(line).append("\n");
		}
		return sb.toString();
	}
	
	
	/**
	 * Mimics org.openmrs.web.Listener.getRuntimeProperties()
	 * 
	 * @param webappName name to use when looking up the runtime properties env var or filename
	 * @return Properties runtime
	 * @throws Exception 
	 */
    @SuppressWarnings("deprecation")
    public String getTestDatasetFilename(String testDatasetName) throws Exception {
		
		InputStream propertiesFileStream = null;
		
		// try to load the file if its a straight up path to the file or
		// if its a classpath path to the file
		if (new File(TEST_DATASETS_PROPERTIES_FILE).exists()) {
			propertiesFileStream = new FileInputStream(TEST_DATASETS_PROPERTIES_FILE);
		} else {
			propertiesFileStream = getClass().getClassLoader().getResourceAsStream(TEST_DATASETS_PROPERTIES_FILE);
			if (propertiesFileStream == null)
				throw new FileNotFoundException("Unable to find '" + TEST_DATASETS_PROPERTIES_FILE + "' in the classpath");
		}
  
		Properties props = new Properties();
		
		OpenmrsUtil.loadProperties(props, propertiesFileStream);

		if (props.getProperty(testDatasetName) == null) {
			throw new Exception ("Test dataset named " + testDatasetName + " not found in properties file");
		}
		
		return props.getProperty(testDatasetName);
	}
	
}
