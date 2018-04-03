package common.pojo;

import java.util.List;

public class PaperAuthorInstiData {

    private int paperId;
    private int authorNameId;
    private int authorRank;
    private int institutionRank;
    private int institutionId;
    private int authorType=0;//EnumType.AuthorType.ORDINARY.getIndex();
    private boolean hypoInsti=false;

    private int groupId;
    @Override
    public String toString() {
        return "PaperAuthorInstiData{" +
                "\npaperId=" + paperId +
                ",\n authorNameId=" + authorNameId +
                ",\n authorRank=" + authorRank +
                ",\n institutionRank=" + institutionRank +
                ",\n institutionId=" + institutionId +
                ",\n authorType=" + authorType +
                ",\n hypoInsti=" + hypoInsti +
                '}';
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isHypoInsti() {
        return hypoInsti;
    }

    public void setHypoInsti(boolean hypoInsti) {
        this.hypoInsti = hypoInsti;
    }

    public int getInstitutionRank() {
        return institutionRank;
    }

    public void setInstitutionRank(int institutionRank) {
        this.institutionRank = institutionRank;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getAuthorNameId() {
        return authorNameId;
    }

    public void setAuthorNameId(int authorNameId) {
        this.authorNameId = authorNameId;
    }

    public int getAuthorRank() {
        return authorRank;
    }

    public void setAuthorRank(int authorRank) {
        this.authorRank = authorRank;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }


    public int getAuthorType() {
        return authorType;
    }

    public void setAuthorType(int authorType) {
        this.authorType = authorType;
    }
}
