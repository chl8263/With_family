package com.example.user.with_family.util;

/**
 * Created by choi on 2017-12-09.
 */

public class SocketDAO {
    private String ip;
    private String mainrecvport;
    private String mainsendport;
    private String soundrecvport;
    private String soundsendport;

    public SocketDAO() {

    }

    public SocketDAO(String ip, String mainrecvport, String mainsendport, String soundrecvport, String soundsendport) {
        this.ip = ip;
        this.mainrecvport = mainrecvport;
        this.mainsendport = mainsendport;
        this.soundrecvport = soundrecvport;
        this.soundsendport = soundsendport;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMainrecvport() {
        return mainrecvport;
    }

    public void setMainrecvport(String mainrecvport) {
        this.mainrecvport = mainrecvport;
    }

    public String getMainsendport() {
        return mainsendport;
    }

    public void setMainsendport(String mainsendport) {
        this.mainsendport = mainsendport;
    }

    public String getSoundrecvport() {
        return soundrecvport;
    }

    public void setSoundrecvport(String soundrecvport) {
        this.soundrecvport = soundrecvport;
    }

    public String getSoundsendport() {
        return soundsendport;
    }

    public void setSoundsendport(String soundsendport) {
        this.soundsendport = soundsendport;
    }
}
