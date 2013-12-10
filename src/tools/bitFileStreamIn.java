package tools;

import java.io.FileInputStream;
import java.io.IOException;

public class bitFileStreamIn {
    FileInputStream in;
    boolean bitBuffer[] = new boolean[8];
    byte byteIn;
    int bcount = 0;
    int fillcount = 0;
    boolean eof = false;
    bitFileStreamIn(FileInputStream i) {
            in = i;
    }
    boolean read() throws IOException {
            
            if(bcount == 0) fillBuffer();
            if(eof) return false;
            bcount--;
            return bitBuffer[7 - bcount];
    }
    void fillBuffer() throws IOException {
            byte bi[] = new byte[1];
            if((in.read(bi)) == -1) {
                    eof = true;
                    return;
            }
            byteIn = bi[0];
            fillcount++;
            if(byteIn >= 0) bitBuffer[0] = false;
            else {
                    bitBuffer[0] = true;
                    byteIn = (byte) (128 + byteIn);
            }
            for(int i=1; i < 8; i++) {
                    if(((byteIn >> (7-i))& 1) == 1) bitBuffer[i] = true;
                    else bitBuffer[i] = false;                      
            }
            bcount = 8;
    }
}

