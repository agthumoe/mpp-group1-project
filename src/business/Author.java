package business;

import ui.Util;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
    private static final long serialVersionUID = 7508481940058530471L;

    private String authorId;
    private String bio;

    public String getBio() {
        return bio;
    }

    public Author(String firstName, String lastName, String telephone, Address address, String bio) {
        super(firstName, lastName, telephone, address);
        this.authorId = Util.getRandom("A-");
        this.bio = bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId='" + authorId + '\'' +
                ", bio='" + bio + '\'' +
                ", firstName" + this.getFirstName() + ", lastName" + this.getLastName() + ", telephone" + this.getTelephone() +
                '}';
    }
}
