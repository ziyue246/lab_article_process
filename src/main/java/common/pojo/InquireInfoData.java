package common.pojo;

import java.util.Date;

public class InquireInfoData {

    private String tableName;//search table name

    private String searchId;//searchbyid

    private Date searchStart;
    private Date searchEnd;


    private int startId;//search start id
    private int lenth;//search start id + lenth


    @Override
    public String toString() {
        return "InquireInfoData{" +
                "\ntableName='" + tableName + '\'' +
                ",\n searchId='" + searchId + '\'' +
                ",\n searchStart=" + searchStart +
                ",\n searchEnd=" + searchEnd +
                ",\n startId=" + startId +
                ",\n lenth=" + lenth +
                ",\n institutionData=" + institutionData +
                ",\n authorData=" + authorData +
                '}';
    }

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
