package common.pojo;

import com.mongodb.client.model.geojson.LineString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorData {
    private int id;
    private String name;
    private int rank;
    private String zhName;
    private String enName;
    private String abbName;
    private String institution;
    private int weight;
    private String titles;
    private int status;//1:正常未修改，-1：不存入数据库
    private String originIds;//在原始表中的ids
    private List<Integer> instiIds;

    private Map<String,Integer> instiCountMap = new HashMap<String, Integer>();

    private String avatar;
    private String zhLastName;
    private String zhFirstName;
    private String enLastName;
    private String enFirstName;
    private String enFirstNameShort;
    private String aliasName;
    private String email;
    private String introduction;
    private String insertTime;


    public Map<String, Integer> getInstiCountMap() {
        return instiCountMap;
    }

    public void setInstiCountMap(Map<String, Integer> instiCountMap) {
        this.instiCountMap = instiCountMap;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<Integer> getInstiIds() {
        return instiIds;
    }

    public void setInstiIds(List<Integer> instiIds) {
        this.instiIds = instiIds;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getZhLastName() {
        return zhLastName;
    }

    public void setZhLastName(String zhLastName) {
        this.zhLastName = zhLastName;
    }

    public String getZhFirstName() {
        return zhFirstName;
    }

    public void setZhFirstName(String zhFirstName) {
        this.zhFirstName = zhFirstName;
    }

    public String getEnLastName() {
        return enLastName;
    }

    public void setEnLastName(String enLastName) {
        this.enLastName = enLastName;
    }

    public String getEnFirstName() {
        return enFirstName;
    }

    public void setEnFirstName(String enFirstName) {
        this.enFirstName = enFirstName;
    }

    public String getEnFirstNameShort() {
        return enFirstNameShort;
    }

    public void setEnFirstNameShort(String enFirstNameShort) {
        this.enFirstNameShort = enFirstNameShort;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

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
