package pl.piomin.services.kafka.common;

public class Info {

    private Long id;
    private String source;
    private String space;
    private String cluster;
    private String message;

    public Info() { }

    public Info(Long id, String source, String space, String cluster, String message) {
        this.id = id;
        this.source = source;
        this.space = space;
        this.cluster = cluster;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", space='" + space + '\'' +
                ", cluster='" + cluster + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
