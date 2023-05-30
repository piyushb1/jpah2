package com.example.jpah2.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer billno;
    @Column
    private String descr;
    @Column
    private Integer rbillno;
    @Column
    private Float amount;
    @Column
    private Float ramount;
    @Column
    private Date billdate;
    @Column
    private Date rdate;
    @Column
    private String cheque;
    @Column
    private String bank;

    public Transaction(){}

    public Transaction(Integer billno, String desc, Integer rbillno, Float amount, Float ramount, Date billdate, Date rdate, String cheque, String bank) {
        this.billno = billno;
        this.descr = desc;
        this.rbillno = rbillno;
        this.amount = amount;
        this.ramount = ramount;
        this.billdate = billdate;
        this.rdate = rdate;
        this.cheque = cheque;
        this.bank = bank;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBillno() {
        return billno;
    }

    public void setBillno(Integer billno) {
        this.billno = billno;
    }

    public Integer getRbillno() {
        return rbillno;
    }

    public void setRbillno(Integer rbillno) {
        this.rbillno = rbillno;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getRamount() {
        return ramount;
    }

    public void setRamount(Float ramount) {
        this.ramount = ramount;
    }

    public Date getBilldate() {
        return billdate;
    }

    public void setBilldate(Date billdate) {
        this.billdate = billdate;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

}
