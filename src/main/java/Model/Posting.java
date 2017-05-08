/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vito Corleone
 */
@XmlRootElement
@Entity
@NamedQueries({
    @NamedQuery(name = "Posting.getAll", query = "select p from Posting as p")
    ,
    @NamedQuery(name = "Posting.findByAuthor", query = "select p from Posting as p where p.author = :author")
    ,
    @NamedQuery(name = "Posting.findBypostingId", query = "select p from Posting as p where p.id = :postingId")
    ,
    @NamedQuery(name = "Posting.findByKeyword", query = "SELECT p FROM Posting p WHERE p.content LIKE :keyword")
})
public class Posting implements Serializable, Comparable<Posting> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String content;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments;
    private Long nextCommentId;
    
//    private Link self;
    
    public Posting() {
    }

    public Posting(String author, String content) {
        this.author = author;
        this.content = content;
        this.date = new Date();
        this.comments = new ArrayList<>();
        this.nextCommentId = 1L;
    }

    public Posting(Long id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.date = new Date();
        this.comments = new ArrayList<>();
        this.nextCommentId = 1L;
    }

    public Long getNextCommentId() {
        return nextCommentId;
    }

    public void setNextCommentId(Long nextCommentId) {
        this.nextCommentId = nextCommentId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String title) {
        this.author = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Posting)) {
            return false;
        }
        Posting other = (Posting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Posting[ id=" + id + " ]";
    }

    public Comment addComment(String author, String message) {
        Comment comment = new Comment(this.nextCommentId++, author, message);
        this.comments.add(comment);
        return comment;
    }

    public boolean removeComment(Long commentId) {
        if (commentId > 0) {
            for (Comment comment : comments) {
                if (comment.getId().equals(commentId)) {
                    comments.remove(comment);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int compareTo(Posting o) {
        return getDate().compareTo(o.getDate());
    }

//    public Link getSelf() {
//        return self;
//    }
//
//    public void setSelf(Link self) {
//        this.self = self;
//    }
    
    
}
