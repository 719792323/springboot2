package stream;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class MyStream extends BufferedInputStream {
    public MyStream(InputStream inputStream) {
        super(inputStream);
    }
}
