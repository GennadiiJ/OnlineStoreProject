package ua.domain;

import javax.persistence.*;

@Entity
@Table(name="Pictures")
public class Picture {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private byte[] body;

    public Picture(){}

    public Picture(String name, byte[] body){
        this.name = name;
        this.body = body;
    }

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

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
