package hello.hellospring.domain;

public class Member {

    private long id; // 고객이 정하는 id가 아닌 시스템이 정하는 id
    private String name; // 이름
    //getter / setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
