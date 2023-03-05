/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author leebe
 */
@Entity
@DiscriminatorValue("MstCustomer")
@PrimaryKeyJoinColumn(name = "USERID")
public class MstCustomer extends MstMember implements Serializable {

    private static final long serialVersionUID = 1L;

    // Attributes
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private String id;
    private String drivingLicense;
    private String icImage;
//

//    public String getId() {
//        return id;
//    }
//    
//    public void setId(String id) {
//        this.id = id;
//    }
    // Constructors
    public MstCustomer() {
    }

    public MstCustomer(String username, String email, String password) {
        this.username = username;
        this.fullname = username;
        this.email = email;
        this.password = password;
        this.userType = "Customer";
        this.status = "Pending";
    }

    // Getters & Setters
    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getIcImage() {
        return icImage;
    }

    public void setIcImage(String icImage) {
        this.icImage = icImage;
    }

    //    Methods
    public static MstCustomer createNewCustomer(String username, String email, String password) {
        MstCustomer member = new MstCustomer(username, email, password);
        return member;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstCustomer)) {
            return false;
        }
        MstCustomer other = (MstCustomer) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Customer[ userId=" + userId + " ]";
    }

}
