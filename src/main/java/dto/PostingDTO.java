/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Vito Corleone
 */
public class PostingDTO {

    private Long id;
    private String author;
    private String content;
    private Date date;
    private List<CommentDTO> comments;

    public PostingDTO() {

    }

    public PostingDTO(Long id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.date = new Date();
        this.comments = new ArrayList<CommentDTO>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

}
