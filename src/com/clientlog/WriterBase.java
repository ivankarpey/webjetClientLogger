package com.clientlog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public abstract class WriterBase implements IWriter {

    static Logger logger = LogManager.getLogger(Infrastructure.class.getName());

    protected String logData;

    @Override
    public void setDataToWrite(BufferedReader data) {

        StringBuffer buffer = new StringBuffer();
        String line = null;
        try {

            while ((line = data.readLine()) != null){
                buffer.append(line);
            }

        } catch (Exception e) {
            logger.error("Can't read post data", e);
        }

        this.logData = buffer.toString();
    }

    @Override
    public void run(){
        write();
    }
}