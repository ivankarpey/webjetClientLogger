package com.clientlog;

import java.io.BufferedReader;

/**
 * Created with IntelliJ IDEA.
 * User: Phoenix
 * Date: 07.11.13
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public interface IWriter extends Runnable {
    public void write();
    public void setDataToWrite(BufferedReader data);
}
