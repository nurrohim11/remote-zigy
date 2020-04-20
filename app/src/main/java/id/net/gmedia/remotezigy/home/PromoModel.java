package id.net.gmedia.remotezigy.home;

public class PromoModel {
    String id, image, url;
    public PromoModel(String id, String image, String url){
        this.id = id;
        this.image =image;
        this.url= url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
