package utils;

import java.io.IOException;
import java.util.Properties;

public enum ApplicationProperties {

    INSTANCE;

    private final Properties properties;

    private final Properties pro;

    ApplicationProperties() {

        properties = new Properties();
        pro = new Properties();
        try{
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            pro.load(getClass().getClassLoader().getResourceAsStream("token.properties"));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public String getbaseUrl(){return properties.getProperty("baseurl");}

    public String get_token(){

        return pro.getProperty("Token");
    }
}
