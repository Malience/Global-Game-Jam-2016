package game;

import java.io.*;

import javax.sound.sampled.*;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.Input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;

public class Listener extends GameComponent implements Controlable
{
	private int micKey = GLFW_KEY_TAB;
	
	// Silence Range ~ 1990 - 2250
	// Voice Range < 1800
	
	public Listener()
	{
		
	}
	
	public int input(float delta)
	{
		if(Input.getKey(micKey))
		{
			startListening();
		}
		
		return 1;
	}
	
	public void startListening()
	{
		System.out.println("LISTENING");
		
		ByteArrayOutputStream byteArrayOutputStream;
        TargetDataLine targetDataLine;
        int count;
        byte tempBuffer[] = new byte[8000];
        int countzero;
   
        short convert[] = new short[tempBuffer.length];
 
 
 
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();

            //while (!stopCapture) 
            //{
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
            //} //end while
        } catch (Exception e) 
        {
            System.out.println(e);
        } //end outer try 
		
		
	}
}
