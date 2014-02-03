/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verteilte.db.module;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author mteeken
 */
@Entity
@NamedQueries({
    @NamedQuery(name="termine.findAll",
                query="SELECT t FROM Terminart t t.id DESC"),
    @NamedQuery(name="termine.search",
                query="SELECT t FROM Terminart t WHERE t.id :id"),
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