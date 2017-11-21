package common.pojo;

import java.util.Date;

public class InquireInfoData {

    private String tableName;//search table name

    private String searchId;//searchbyid

    private Date searchStart;
    private Date searchEnd;


    private int startId;//search start id
    private int lenth;//search start id + lenth


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getStartId() {
        return startId;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

    public int getLenth() {
        return lenth;
    }

    public void setLenth(int lenth) {
        this.lenth = lenth;
    }


    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public Date getSearchStart() {
        return searchStart;
    }

    public void setSearchStart(Date searchStart) {
        this.searchStart = searchStart;
    }

    public Date getSearchEnd() {
        return searchEnd;
    }

    public void setSearchEnd(Date searchEnd) {
        this.searchEnd = searchEnd;
    }



    private InstitutionData institutionData;

    public InstitutionData getInstitutionData() {
        return institutionData;
    }

    public void setInstitutionData(InstitutionData institutionData) {
        this.institutionData = institutionData;
    }


    private AuthorData authorData;

    public AuthorData getAuthorData() {
        return authorData;
    }

    public void setAuthorData(AuthorData authorData) {
        this.authorData = authorData;
    }
}
