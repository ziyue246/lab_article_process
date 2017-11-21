package common.pojo;

public class InstitutionData {

    private int id;
    private String name;
    private int weight;
    private String titles;
    private String correctName;
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


    public String getCorrectName() {
        return correctName;
    }

    public void setCorrectName(String correctName) {
        this.correctName = correctName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InstitutionData{" +
                "\nid=" + id +
                ", \nname='" + name + '\'' +
                ", \nweight=" + weight +
                ", \ntitles='" + titles + '\'' +
                '}';
    }
}
