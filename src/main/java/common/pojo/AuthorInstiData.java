package common.pojo;

import java.util.List;

public class AuthorInstiData {

    private String authorAbbName;
    private String authorZhName;
    private String authorEnName;
    private int authorRank;
    private List<String> institutions;

    private int authorNameId;
    private List<Integer> institutionIds;
    private int paperId;


    public int getAuthorNameId() {
        return authorNameId;
    }

    public void setAuthorNameId(int authorNameId) {
        this.authorNameId = authorNameId;
    }

    public List<Integer> getInstitutionIds() {
        return institutionIds;
    }

    public void setInstitutionIds(List<Integer> institutionIds) {
        this.institutionIds = institutionIds;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getAuthorRank() {
        return authorRank;
    }

    public void setAuthorRank(int authorRank) {
        this.authorRank = authorRank;
    }

    public List<String> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<String> institutions) {
        this.institutions = institutions;
    }


    public String getAuthorAbbName() {
        return authorAbbName;
    }

    public void setAuthorAbbName(String authorAbbName) {
        this.authorAbbName = authorAbbName;
    }


    public String getAuthorZhName() {
        return authorZhName;
    }

    public void setAuthorZhName(String authorZhName) {
        this.authorZhName = authorZhName;
    }

    public String getAuthorEnName() {
        return authorEnName;
    }

    public void setAuthorEnName(String authorEnName) {
        this.authorEnName = authorEnName;
    }
}
