package common.pojo;

import java.util.Date;

public class InstitutionData {

    private int id;
    private String name;
    private int rank;
    private int weight;
    private String titles;
    private String correctName;
    private String nameZh;
    private String nameEn;
    private Date insertTime;
    private int status;//1:����δ�޸ģ�-1�����������ݿ�

    private String introduction;

    private String originIds;//��ԭʼ���е�ids

    private int originId;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    public String getOriginIds() {
        return originIds;
    }

    public void setOriginIds(String originIds) {
        this.originIds = originIds;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }


    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
