package clases.inra;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformacionDescargaAdjuntos", propOrder = {
    "host",
    "port",
    "userName",
    "password",
    "dir"
})
public class InformacionDescargaAdjuntos {

    protected String host;
    protected String port;
    protected String userName;
    protected String password;
    protected String dir;

    public String getHost() {
        return host;
    }
    public void setHost(String value) {
        this.host = value;
    }
    
    
    public String getPort() {
        return port;
    }
    public void setPort(String value) {
        this.port = value;
    }
    

    public String getUserName() {
        return userName;
    }
    public void setUserName(String value) {
        this.userName = value;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String value) {
        this.password = value;
    }


    public String getDir() {
        return dir;
    }
    public void setDir(String value) {
        this.dir = value;
    }
}