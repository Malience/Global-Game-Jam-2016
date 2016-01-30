package game;

import java.io.*;
import javax.sound.sampled.*;

public class Listener 
{
	public Listener()
	{
		
	}
	
	public void startListening()
	{
		ByteArrayOutputStream byteArrayOutputStream;
        TargetDataLine targetDataLine;
        int count;
        boolean stopCapture = false;
        byte tempBuffer[] = new byte[8000];
        int countzero;
   
        short convert[] = new short[tempBuffer.length];
 
 
 
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            stopCapture = false;

            while (!stopCapture) 
            {
                AudioFormat audioFormat = new AudioFormat(8000.0F, 16, 1, true, false);
                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
                targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                targetDataLine.open(audioFormat);
                targetDataLine.start();
                count = targetDataLine.read(tempBuffer, 0, tempBuffer.length);
                byteArrayOutputStream.write(tempBuffer, 0, count); 
                try 
                {
                    countzero = 0;
                    for (int i = 0; i < tempBuffer.length; i++) {                                     
                        convert[i] = tempBuffer[i];
                        if (convert[i] == 0) 
                        {
                            countzero++;
                        }
                    }
                     

                    System.out.println("MIC: " + countzero);
 
                } 
                catch (StringIndexOutOfBoundsException e) 
                {
                    System.out.println(e.getMessage());
                }
                Thread.sleep(0);
                targetDataLine.close();
            } //end while
        } catch (Exception e) 
        {
            System.out.println(e);
        } //end outer try
	}
}
