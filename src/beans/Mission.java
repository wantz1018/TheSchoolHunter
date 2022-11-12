package beans;

public class Mission {
    private String id;
    private String icon;
    private String title;
    private String content;
    private String mdate;
    private String mplace;
    private String rewards;

    public String getId() {
        return id;
    }

    public Mission(){}

    public Mission(String id, String icon, String title, String content, String mdate, String mplace, String rewards) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.content = content;
        this.mdate = mdate;
        this.mplace = mplace;
        this.rewards = rewards;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getMplace() {
        return mplace;
    }

    public void setMplace(String mplace) {
        this.mplace = mplace;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }
}
