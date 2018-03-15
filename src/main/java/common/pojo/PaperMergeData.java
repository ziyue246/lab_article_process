package common.pojo;

import java.util.Date;
import java.util.List;


public class PaperMergeData {


    private int id;

    private String title;
    private String authors;
    private String institutions;
    private String reprintAuthor;
    private String reprintInstitution;
    private Date pubdate;
    private String publisher;
    private Date insertTime;
    private String brief;
    private String journal;
    private String keywords;
    private String fund;
    private String category;
    private String volume;
    private String issue;
    private String pageCode;
    private String doi;
    private double impactFactor2year;
    private double impactFactor5year;
    private String jcr;
    private String sourceTitle;
    private String conferenceDate;
    private String conferenceLocation;
    private String categoryCode;
    private DbDataOpreration dbStatus=DbDataOpreration.NULL;// 0:in db,1:need insert,2:nedd update

    private int inSci;
    private int sciDown;
    private int sciRefer;
    private int sciDataId;

    private int inEi;
    private int eiDown;
    private int eiRefer;
    private int eiDataId;

    private int inCnki;
    private int CnkiDown;
    private int CnkiRefer;
    private int CnkiDataId;
    private String formatStr;

    private int documentType;


    private List<AuthorData> authorDataList;

    private List<InstitutionData> institutionDataList;

    private List<AuthorData> reprintAuthorDataList;

    private List<InstitutionData> reprintInstitutionDataList;





    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getInstitutions() {
        return institutions;
    }

    public void setInstitutions(String institutions) {
        this.institutions = institutions;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public double getImpactFactor2year() {
        return impactFactor2year;
    }

    public void setImpactFactor2year(double impactFactor2year) {
        this.impactFactor2year = impactFactor2year;
    }

    public double getImpactFactor5year() {
        return impactFactor5year;
    }

    public void setImpactFactor5year(double impactFactor5year) {
        this.impactFactor5year = impactFactor5year;
    }

    public String getJcr() {
        return jcr;
    }

    public void setJcr(String jcr) {
        this.jcr = jcr;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getConferenceDate() {
        return conferenceDate;
    }

    public void setConferenceDate(String conferenceDate) {
        this.conferenceDate = conferenceDate;
    }

    public String getConferenceLocation() {
        return conferenceLocation;
    }

    public void setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public int getInSci() {
        return inSci;
    }

    public void setInSci(int inSci) {
        this.inSci = inSci;
    }

    public int getSciDown() {
        return sciDown;
    }

    public void setSciDown(int sciDown) {
        this.sciDown = sciDown;
    }

    public int getSciRefer() {
        return sciRefer;
    }

    public void setSciRefer(int sciRefer) {
        this.sciRefer = sciRefer;
    }

    public int getSciDataId() {
        return sciDataId;
    }

    public void setSciDataId(int sciDataId) {
        this.sciDataId = sciDataId;
    }

    public int getInEi() {
        return inEi;
    }

    public void setInEi(int inEi) {
        this.inEi = inEi;
    }

    public int getEiDown() {
        return eiDown;
    }

    public void setEiDown(int eiDown) {
        this.eiDown = eiDown;
    }

    public int getEiRefer() {
        return eiRefer;
    }

    public void setEiRefer(int eiRefer) {
        this.eiRefer = eiRefer;
    }

    public int getEiDataId() {
        return eiDataId;
    }

    public void setEiDataId(int eiDataId) {
        this.eiDataId = eiDataId;
    }

    public int getInCnki() {
        return inCnki;
    }

    public void setInCnki(int inCnki) {
        this.inCnki = inCnki;
    }

    public int getCnkiDown() {
        return CnkiDown;
    }

    public void setCnkiDown(int cnkiDown) {
        CnkiDown = cnkiDown;
    }

    public int getCnkiRefer() {
        return CnkiRefer;
    }

    public void setCnkiRefer(int cnkiRefer) {
        CnkiRefer = cnkiRefer;
    }

    public int getCnkiDataId() {
        return CnkiDataId;
    }

    public void setCnkiDataId(int cnkiDataId) {
        CnkiDataId = cnkiDataId;
    }


    public List<AuthorData> getAuthorDataList() {
        return authorDataList;
    }

    public void setAuthorDataList(List<AuthorData> authorDataList) {
        this.authorDataList = authorDataList;
    }

    public List<InstitutionData> getInstitutionDataList() {
        return institutionDataList;
    }

    public void setInstitutionDataList(List<InstitutionData> institutionDataList) {
        this.institutionDataList = institutionDataList;
    }


    public String getFormatStr() {
        return formatStr;
    }

    public void setFormatStr(String formatStr) {
        this.formatStr = formatStr;
    }


    public DbDataOpreration getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(DbDataOpreration dbStatus) {
        this.dbStatus = dbStatus;
    }


    public List<AuthorData> getReprintAuthorDataList() {
        return reprintAuthorDataList;
    }

    public void setReprintAuthorDataList(List<AuthorData> reprintAuthorDataList) {
        this.reprintAuthorDataList = reprintAuthorDataList;
    }

    public List<InstitutionData> getReprintInstitutionDataList() {
        return reprintInstitutionDataList;
    }

    public void setReprintInstitutionDataList(List<InstitutionData> reprintInstitutionDataList) {
        this.reprintInstitutionDataList = reprintInstitutionDataList;
    }

    public String getReprintAuthor() {
        return reprintAuthor;
    }

    public void setReprintAuthor(String reprintAuthor) {
        this.reprintAuthor = reprintAuthor;
    }

    public String getReprintInstitution() {
        return reprintInstitution;
    }

    public void setReprintInstitution(String reprintInstitution) {
        this.reprintInstitution = reprintInstitution;
    }
}
