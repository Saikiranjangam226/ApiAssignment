package lib;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadProperties extends Wrappers {
	public static Properties prop;
	
	public ReadProperties(){
		try {
			prop = new Properties();
			prop.load(new FileInputStream(new File("./src/test/resources/config.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getbaseurl() {
		 String baseurl = prop.getProperty("Baseurl");
		 return baseurl;
	}
	
	

	

}
