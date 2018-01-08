package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

/**
 * Created by erlangga on 7/24/2017.
 */

public class ImageUploadApp {
    public String name;
    public String url;
    public String dataSite;
    public String status;
    public String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName()
    {
        return name;
    }
    public String getUrl()
    {
        return url;
    }

    public ImageUploadApp(String name, String url)
    {
        this.name=name;
        this.url = url;
        this.dataSite = dataSite;
        this.status = status;
        this.key = key;
    }
    public ImageUploadApp(String status, String url, String name, String dataSite, String key){

        this.name=name;
        this.url = url;
        this.dataSite = dataSite;
        this.status = status;
        this.key = key;

    }
    public ImageUploadApp(){

    }

    public String getDataSite() {
        return dataSite;
    }

    public void setDataSite(String dataSite) {
        this.dataSite = dataSite;
    }
    @Override
    public String toString() {
        return dataSite;
    }

}
