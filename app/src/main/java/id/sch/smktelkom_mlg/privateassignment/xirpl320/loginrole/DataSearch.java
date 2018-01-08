package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;
/**
 * Created by erlangga on 7/31/2017.
 */

public class DataSearch {
    String dataPid;
    String dataSite;

    public void setDataPid(String dataPid) {
        this.dataPid = dataPid;
    }

    String dataSid;

    public void setDataSite(String dataSite) {
        this.dataSite = dataSite;
    }

    public void setDataSid(String dataSid) {
        this.dataSid = dataSid;
    }

    public DataSearch(){}

    public DataSearch(String dataPid,String dataSite,String dataSid)
    {
        this.dataPid = dataPid;
        this.dataSite = dataSite;
        this.dataSid = dataSid;
    }
    public String getDataPid(){
        return dataPid;
    }
    public String getDataSite(){
        return dataSite;
    }
    public String getDataSid(){
        return dataSid;
    }

    @Override
    public String toString() {
        return dataSite;
    }

}
