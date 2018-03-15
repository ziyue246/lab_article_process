package common.pojo;

import java.util.Date;
import java.util.List;

public class PaperData {

    private int id;
    private String url;
    private String title;
    private String author;
    private String address;
    private String reprintAuthor;
    private String reprintInstitution;
    private String pubtime;
    private Date pubdate;
    private String publisher;
    private Date insertTime;
    private String brief;
    private String journal;
    private String keywords;
    private String referUrl;
    private String md5;
    private String citeUrl;
    private int referNum;
    private int citeNum;
    private String fund;
    private String category;
    private String downUrl;
    private int downNum;
    private String labCategory;
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

    private String documentType;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    private List<AuthorData> authorDataList;

    private List<InstitutionData> institutionDataList;


    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setCiteUrl(String citeUrl) {
        this.citeUrl = citeUrl;
    }

    public void setReferNum(int referNum) {
        this.referNum = referNum;
    }

    public void setCiteNum(int citeNum) {
        this.citeNum = citeNum;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public void setDownNum(int downNum) {
        this.downNum = downNum;
    }

    public void setLabCategory(String labCategory) {
        this.labCategory = labCategory;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setImpactFactor2year(double impactFactor2year) {
        this.impactFactor2year = impactFactor2year;
    }

    public void setImpactFactor5year(double impactFactor5year) {
        this.impactFactor5year = impactFactor5year;
    }

    public void setJcr(String jcr) {
        this.jcr = jcr;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public void setConferenceDate(String conferenceDate) {
        this.conferenceDate = conferenceDate;
    }

    public void setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAddress() {
        return address;
    }

    public String getPubtime() {
        return pubtime;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public String getPublisher() {
        return publisher;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public String getBrief() {
        return brief;
    }

    public String getJournal() {
        return journal;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public String getMd5() {
        return md5;
    }

    public String getCiteUrl() {
        return citeUrl;
    }

    public int getReferNum() {
        return referNum;
    }

    public int getCiteNum() {
        return citeNum;
    }

    public String getFund() {
        return fund;
    }

    public String getCategory() {
        return category;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public int getDownNum() {
        return downNum;
    }

    public String getLabCategory() {
        return labCategory;
    }

    public String getVolume() {
        return volume;
    }

    public String getIssue() {
        return issue;
    }

    public String getPageCode() {
        return pageCode;
    }

    public String getDoi() {
        return doi;
    }

    public double getImpactFactor2year() {
        return impactFactor2year;
    }

    public double getImpactFactor5year() {
        return impactFactor5year;
    }

    public String getJcr() {
        return jcr;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public String getConferenceDate() {
        return conferenceDate;
    }

    public String getConferenceLocation() {
        return conferenceLocation;
    }

    public String getCategoryCode() {
        return categoryCode;
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

    @Override
    public String toString() {
        return "PaperDate{" +
                "\n id=" + id +
                ",\n url='" + url + '\'' +
                ",\n title='" + title + '\'' +
                ",\n author='" + author + '\'' +
                ",\n address='" + address + '\'' +
                ",\n pubtime='" + pubtime + '\'' +
                ",\n pubdate=" + pubdate +
                ",\n publisher='" + publisher + '\'' +
                ",\n insertTime=" + insertTime +
                ",\n brief='" + brief + '\'' +
                ",\n journal='" + journal + '\'' +
                ",\n keywords='" + keywords + '\'' +
                ",\n referUrl='" + referUrl + '\'' +
                ",\n md5='" + md5 + '\'' +
                ",\n citeUrl='" + citeUrl + '\'' +
                ",\n referNum=" + referNum +
                ",\n citeNum=" + citeNum +
                ",\n fund='" + fund + '\'' +
                ",\n category='" + category + '\'' +
                ",\n downUrl='" + downUrl + '\'' +
                ",\n downNum=" + downNum +
                ",\n labCategory='" + labCategory + '\'' +
                ",\n volume='" + volume + '\'' +
                ",\n issue='" + issue + '\'' +
                ",\n pageCode='" + pageCode + '\'' +
                ",\n doi='" + doi + '\'' +
                ",\n impactFactor2year=" + impactFactor2year +
                ",\n impactFactor5year=" + impactFactor5year +
                ",\n jcr='" + jcr + '\'' +
                ",\n sourceTitle='" + sourceTitle + '\'' +
                ",\n conferenceDate='" + conferenceDate + '\'' +
                ",\n conferenceLocation='" + conferenceLocation + '\'' +
                ",\n categoryCode='" + categoryCode + '\'' +
                '}';
    }
}
