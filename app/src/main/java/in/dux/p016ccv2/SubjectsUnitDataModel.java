package in.dux.p016ccv2;

public class SubjectsUnitDataModel {
    private String title;
    private String link;

    public SubjectsUnitDataModel(String mTitle, String mLink) {
        title = mTitle;
        link = mLink;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
