package sj.springboot.learn.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "address")
public class Address {
    public String ip;
    public int myPort;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getMyPort() {
        return myPort;
    }

    public void setMyPort(int myPort) {
        this.myPort = myPort;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ip='" + ip + '\'' +
                ", myPort=" + myPort +
                '}';
    }
}
