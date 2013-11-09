package com.clientlog;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiskWriter extends WriterBase {

    static Logger logger = LogManager.getLogger(DiskWriter.class.getName());

    @Override
    public void write() {
        logger.log(Level.ALL, this.logData);
    }

}
