package tools;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

public class bitFileStreamOut2 extends FilterOutputStream {
    FileOutputStream out;
    boolean bitBuffer[] = new boolean[8];
    int bufferCount = 0;
    bitFileStreamOut2(FileOutputStream o) {
            super(o);
    }
    
    int bits;
    int offset;
 

   /* void write(boolean wbit) throws IOException {
            bitBuffer[bufferCount] = wbit;
            bufferCount++;
            if(bufferCount == 8) 
            	flushBuffer();
    }*/
    void flushBuffer() throws IOException {
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
    public void writeBit(int bit) throws IOException {
    	if (bit==0) {
            bits<<=1;
        }
        else {
            bits=bits<<1|1;
        }
        offset++;
        if (offset==8) {
            write(bits);
            bits=0;
            offset=0;
        }
    }
    
    /** flush the output stream.
     */
    public void flush() throws IOException {
        if (offset!=0) {
            write(bits<<(8-offset));
            offset=0;
        }
        super.flush();
    }
    /** close  the output stream.
     */
    public void close() throws IOException {
        flush();
        super.close();
    }

    
}
