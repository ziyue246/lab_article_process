package common.pojo;

public class AuthorData {
    private int id;
    private String name;
    private String abbName;
    private String institution;
    private int weight;
    private String titles;
    private int status;//1:正常未修改，-1：不存入数据库
    private String originIds;//在原始表中的ids

    public String getOriginIds() {
        return originIds;
    }

    public void setOriginIds(String originIds) {
        this.originIds = originIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAbbName() {
        return abbName;
    }

    public void setAbbName(String abbName) {
        this.abbName = abbName;
    }
}
