/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johan
 */
public class LoggerManager {

    private static LoggerManager INSTANCE = null;
    private static Handler handler;
    private static final Logger logger = Logger.getLogger(LoggerManager.class.getName());

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (LoggerManager.class) {

                if (INSTANCE == null) {
                    INSTANCE = new LoggerManager();
                }
            }
        }
    }

    public static LoggerManager getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public static void Open() {
        try {
            File theDir = new File("logs");
            if (!theDir.exists()) {
                boolean result = false;
                try {
                    theDir.mkdir();
                    result = true;
                } catch (SecurityException se) {
                    System.out.println("ferreteria_las_vegas.utils.LoggerManager.Open()");
                }
                if (result) {
                    System.out.println("Logs up");
                }
            }
            DateTimeFormatter dt2 = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-a");
            handler = new FileHandler("logs/" + LocalDateTime.now().format(dt2) + ".log", true);
            logger.addHandler(handler);
            logger.setLevel(Level.INFO);
            logger.info("App Start");
        } catch (ExceptionInInitializerError | IOException | SecurityException ex) {
            System.out.println(ex);
        }
    }

    public static void Close() {
        try {
            logger.info("App Close");
            handler.close();
        } catch (SecurityException ex) {
            System.out.println(ex);
        }
    }

    public static Logger Logger() {
        return logger;
    }
}
