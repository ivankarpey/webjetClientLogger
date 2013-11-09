package com.clientlog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Phoenix
 * Date: 07.11.13
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class Infrastructure {

    static Logger logger = LogManager.getLogger(Infrastructure.class.getName());

    static final String MODE = getMode();

    private static String getMode(){
        String result = "disk";
        try{

            Properties settings = new Properties();
            settings.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            result = settings.getProperty("mode");

        }
        catch(Exception e){
            logger.error("Couldn't read settings file", e);
        }
        return result;
    }

    private static class SingletonHolder {
        static final ExecutorService INSTANCE = Executors.newCachedThreadPool();
    }

    public static ExecutorService threadPool() {
        return SingletonHolder.INSTANCE;
    }

    public static IWriter getWriter(){
        return MODE.equals("disk") ? new DiskWriter() : new DatabaseWriter();
    }
}
