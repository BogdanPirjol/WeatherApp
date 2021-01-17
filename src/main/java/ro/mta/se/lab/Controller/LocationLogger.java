package ro.mta.se.lab.Controller;

import java.io.FileWriter;
import java.io.IOException;

public class LocationLogger {
    private static String messageToLog;

    public static String updateHistoryRecord()
    {
        if(messageToLog.isEmpty()) {
            return "Cannot record`\nReason: missing message for recording!";
        }
            try{
                FileWriter writer= new FileWriter("HistoryRecords.txt", true);
                writer.write(messageToLog);
                writer.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        return messageToLog;
    }

    public void setMessageForLog(String messageForLog)
    {
        messageToLog="";
        messageToLog= new String(messageForLog);
    }

}
