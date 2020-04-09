package id.net.gmedia.remotezigy.channel;

public class ChannelModel {
    String id, icon, nama, link;
    public ChannelModel(String id, String icon, String nama, String link){
        this.icon = icon;
        this.id = id;
        this.nama = nama;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
