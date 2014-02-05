/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author mteeken
 */
@Entity
@NamedQueries({
    @NamedQuery(name="termine.findAll",
                query="SELECT t FROM Termine t ORDER BY t.id DESC"),
    @NamedQuery(name="termine.findNextX",
                query="SELECT t FROM Termine t WHERE t.date_begin > :date"),
    @NamedQuery(name="termine.find",
                query="SELECT t FROM Termine t WHERE t.id = :id"),
    @NamedQuery(name="termine.search",
                query="SELECT t FROM Termine t WHERE t.id like :id"),
})
public class Termine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "date_begin") 
    private java.sql.Timestamp date_begin;
    @Column(name="date_end")
    private java.sql.Timestamp date_end;
    @Column(name="title")
    String title;
    @Column(name="ort")
    String ort;
    @Column(name="terminart")
    Terminart art;
    @ManyToOne(cascade={CascadeType.MERGE})
    Nutzer user;
    
    public Termine() {
        
    }
    
    public Termine(String title, String date_begin, String date_end,
           String ort, Terminart art, Nutzer n) throws ParseException {

        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateBegin = isoFormat.parse(date_begin);
        Date dateEnd = isoFormat.parse(date_end);
        
        this.date_begin = new Timestamp(dateBegin.getTime());
        this.date_end = new Timestamp(dateEnd.getTime());
        this.title = title;
        this.ort = ort;
        this.art = art;
        this.user = n;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(Timestamp date_begin) {
        this.date_begin = date_begin;
    }

    public Timestamp getDate_end() {
        return date_end;
    }

    public void setDate_end(Timestamp date_end) {
        this.date_end = date_end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Terminart getArt() {
        return art;
    }

    public void setArt(Terminart art) {
        this.art = art;
    }

    public Nutzer getUser() {
        return user;
    }

    public void setUser(Nutzer user) {
        this.user = user;
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
        if (!(object instanceof Termine)) {
            return false;
        }
        Termine other = (Termine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.termine[ id=" + id + " ]";
    }
    
}
