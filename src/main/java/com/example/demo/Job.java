package com.example.demo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.time.LocalDate;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date date;
    private String author;
    private String phoneNum;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Job() {
    }

    public Job(String title, String description, Date date, String author, String phoneNum) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.author = author;
        this.phoneNum = phoneNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", postedDate=" + date +
                ", author='" + author + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}