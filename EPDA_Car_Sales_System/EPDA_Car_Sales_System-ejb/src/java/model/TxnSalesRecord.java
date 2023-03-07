/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author leebe
 */
@Entity
public class TxnSalesRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final double bookingFee = 15;

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String salesId;
    private MstCar car;

    @ManyToOne
    private MstCustomer customer;

    @ManyToOne
    private MstMember salesman;

    private double totalPayable;
    private String orderStatus;
    private String salesmanComments;
    private String customerReview;
    private float salesmanRating;
    private float carRating;
    private LocalDateTime salesDate;

    // Getters & Setters
    public String getSalesId() {
        return salesId;
    }
    
    public static Double getBookingFee() {
        return bookingFee;
    }

//    public void setSalesId(String salesId) {
//        this.salesId = salesId;
//    }
    public MstCar getCar() {
        return car;
    }

    public void setCar(MstCar car) {
        this.car = car;
    }

    public MstCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(MstCustomer customer) {
        this.customer = customer;
    }

    public MstMember getSalesman() {
        return salesman;
    }

    public void setSalesman(MstMember salesman) {
        this.salesman = salesman;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSalesmanComments() {
        return salesmanComments;
    }

    public void setSalesmanComments(String salesmanComments) {
        this.salesmanComments = salesmanComments;
    }

    public String getCustomerReview() {
        return customerReview;
    }

    public void setCustomerReview(String customerReview) {
        this.customerReview = customerReview;
    }

    public float getSalesmanRating() {
        return salesmanRating;
    }

    public void setSalesmanRating(float salesmanRating) {
        this.salesmanRating = salesmanRating;
    }

    public float getCarRating() {
        return carRating;
    }

    public void setCarRating(float carRating) {
        this.carRating = carRating;
    }

       
    public LocalDateTime getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDateTime salesDate) {
        this.salesDate = salesDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salesId != null ? salesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxnSalesRecord)) {
            return false;
        }
        TxnSalesRecord other = (TxnSalesRecord) object;
        if ((this.salesId == null && other.salesId != null) || (this.salesId != null && !this.salesId.equals(other.salesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Sales_Record[ id=" + salesId + " ]";
    }

}
