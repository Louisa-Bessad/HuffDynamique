package tools;

import java.io.FileOutputStream;
import java.io.IOException;

public class bitFileStreamOut {
    FileOutputStream out;
    boolean bitBuffer[] = new boolean[8];
    int bufferCount = 0;
    public bitFileStreamOut(FileOutputStream a) {
            out = a;
    }
    public void write(boolean wbit) throws IOException {
            bitBuffer[bufferCount] = wbit;
            bufferCount++;
            if(bufferCount == 8) flushBuffer();

    }
    
    public void writer(byte[] b) throws IOException{
    	out.write(b);
    }
    public void flushBuffer() throws IOException {
            byte tempval = 0;
            for (int i=7; i > 0; i--) {
                    if(bitBuffer[i] == true) {
                            int aval = 1;
                            aval = aval<<(7-i);
                            tempval += aval;
                    }
            }
            if(bitBuffer[0] == false) out.write(tempval);
            else {
                    tempval = (byte) (-128 + tempval);
                    out.write(tempval);
            }
            bufferCount = 0;
    }
}
