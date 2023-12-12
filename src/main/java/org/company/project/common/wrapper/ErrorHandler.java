package org.company.project.common.wrapper;

import org.apache.log4j.Logger;
import org.company.project.common.exception.MicroServiceRunnerAnnotationNotFound;
import org.company.project.common.exception.RestConfigurationAnnotationNotFound;
import org.company.project.common.exception.ServerCouldNotStart;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class);
    private ErrorHandler () {}
    public static Map<String, String> getError (Exception e){
        e.printStackTrace();
        LOGGER.error(e.getClass() + " : " + e.getMessage());
        Map<String, String> map = new HashMap<>();
        if (e instanceof ArithmeticException){
            map.put("ERROR CODE", "100");
            map.put("ERROR MESSAGE", "Arithmetic Error");
        } else if (e instanceof SQLException) {
            map.put("ERROR CODE", "101");
            map.put("ERROR MESSAGE", "DataBase Error");
        }else {
            map.put("ERROR CODE", "1000");
            map.put("ERROR MESSAGE", "Unknown Error");
        }
        return map;
    }
}
