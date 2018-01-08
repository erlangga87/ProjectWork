package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

/**
 * Created by erlangga on 7/24/2017.
 */

public class ImageUploadEdit {
    public String name;
    public String url;
    public String dataSite;
    public String status;
    public String key;
    public String imgName;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

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

    public ImageUploadEdit(String name,String url,String dataSite,String status,String key,String imgName)
    {
        this.name=name;
        this.url = url;
        this.dataSite = dataSite;
        this.status = status;
        this.key = key;
        this.imgName = imgName;
    }
    public ImageUploadEdit()
    {

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
