/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author mteeken
 */
@Entity
@NamedQueries({
    @NamedQuery(name="nutzer.find",
                query="SELECT n FROM Nutzer n WHERE n.name like :name AND n.password like :password"),
    @NamedQuery(name="nutzer.create",
                query="SELECT n FROM Nutzer n WHERE n.name like :name"),
})
public class Nutzer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    private String password;
    
    public Nutzer() {

    }

    public Nutzer(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nutzer)) {
            return false;
        }
        Nutzer other = (Nutzer) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.User[ name=" + name + " ]";
    }
    
}
