/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.google.common.hash.Hashing;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import rest.KwetterREST;

/**
 *
 * @author Vito Corleone
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "User.getAll", query = "select u from User as u")
    ,
    @NamedQuery(name = "User.findByEmailAddress", query = "select u from User as u where u.emailAddress = :emailAddress")
    ,
    @NamedQuery(name = "User.findByID", query = "select u from User as u where u.id = :userId")
})
//@InjectLink(
//            resource = KwetterREST.class,
//            style = Style.ABSOLUTE,
//            rel = "self",
//            bindings = @Binding(name = "id", value = "${instance.id}"),
//            method = "get"
//    )
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // properties
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "peopleThatFollowMe")
    private List<User> peopleThatIFollow;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<User> peopleThatFollowMe;

    private String userRole;
    private String bio;
    private String website;
    private String location;
    private String name;
    private String photoPath;

    @Column(unique = true)
    private String emailAddress;

    private String password;

    private transient List<String> resources = new ArrayList<>();

    // empty constructor
    public User() {

    }

    public User(String name, String emailAddres, String password) {
        this.name = name;
        this.emailAddress = emailAddres;
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        peopleThatIFollow = new ArrayList<>();
        peopleThatFollowMe = new ArrayList<>();
        this.userRole = "User";
        this.bio = "-";
        this.location = "-";
        this.website = "-";
        this.photoPath = "http://www.denieuwereporter.nl/wp-content/uploads/2015/06/twitter.jpg";
    }

    // getters and setters
    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public void addResource(String resource) {
        this.resources.add(resource);
        System.out.println("Model.User.addResource()" + resource);
        System.out.println("Model.User.addResource()" + this.resources.size());
    }

    public List<User> getThePeopleThatIFollow() {
        return peopleThatIFollow;
    }

    public void setThePeopleThatIFollow(ArrayList<User> following) {
        this.peopleThatIFollow = following;
    }

    public List<User> getThePeopleThatFollowMe() {
        return peopleThatFollowMe;
    }

    public void setThePeopleThatFollowMe(ArrayList<User> followers) {
        this.peopleThatFollowMe = followers;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.User[ id=" + id + " ]";
    }

    // methods
    /**
     * this method adds a friend to the user
     *
     * @param newFriend the user that gets added as a friend
     * @return true if user is added as a friend
     */
    public boolean addUserThatIWantToFollow(User toFollow) {
        if (toFollow != null) {
            for (User user : peopleThatIFollow) {
                if (user.getEmailAddress().equals(toFollow.getEmailAddress())) {
                    return false;
                }
            }
        }
        peopleThatIFollow.add(toFollow);
        toFollow.addUserThatFollowsMe(this);
        return true;
    }

    public boolean addUserThatFollowsMe(User followsMe) {
        if (followsMe != null) {
            for (User user : peopleThatFollowMe) {
                if (user.getEmailAddress().equals(followsMe.getEmailAddress())) {
                    return false;
                }
            }
        }
        peopleThatFollowMe.add(followsMe);
        return true;
    }

    /**
     * this method removes the provided friend of the user
     *
     * @param oldFriend the friend that needs to be removed
     * @return true if peopleThatIFollow was deleted
     */
    public boolean deleteUserThatIFollow(User oldFriend) {
        if (oldFriend != null) {
            for (User user : peopleThatIFollow) {
                if (user.getEmailAddress().equals(oldFriend.getEmailAddress())) {
                    peopleThatIFollow.remove(oldFriend);
                    oldFriend.deleteUserThatFollowsMe(this);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteUserThatFollowsMe(User oldFriend) {
        if (oldFriend != null) {
            for (User user : peopleThatFollowMe) {
                if (user.getEmailAddress().equals(oldFriend.getEmailAddress())) {
                    peopleThatFollowMe.remove(oldFriend);
                    return true;
                }
            }
        }
        return false;
    }

//    public Link getSelf() {
//        return self;
//    }
//
//    public void setSelf(Link self) {
//        this.self = self;
//    }
}
