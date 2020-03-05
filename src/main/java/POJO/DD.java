package POJO;

public class DD {
    private String domain;
    private String ipAddress;
    private String httpStatus;
    private String allTime;
    private String dnsTime;
    private String connTime;
    private String downTime;
    private String fileSize;
    private String downSpeed;

    public DD(String domain, String ipAddress, String httpStatus, String allTime, String dnsTime, String connTime, String downTime, String fileSize, String downSpeed) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.httpStatus = httpStatus;
        this.allTime = allTime;
        this.dnsTime = dnsTime;
        this.connTime = connTime;
        this.downTime = downTime;
        this.fileSize = fileSize;
        this.downSpeed = downSpeed;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAllTime() {
        return allTime;
    }

    public void setAllTime(String allTime) {
        this.allTime = allTime;
    }

    public String getDnsTime() {
        return dnsTime;
    }

    public void setDnsTime(String dnsTime) {
        this.dnsTime = dnsTime;
    }

    public String getConnTime() {
        return connTime;
    }

    public void setConnTime(String connTime) {
        this.connTime = connTime;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDownSpeed() {
        return downSpeed;
    }

    public void setDownSpeed(String downSpeed) {
        this.downSpeed = downSpeed;
    }





}
