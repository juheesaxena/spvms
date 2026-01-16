package com.spvms.spvms.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private Boolean compliant = false;

    @OneToMany(mappedBy = "vendor")
    @JsonIgnore   // 🔴 stops loop
    private List<VendorQuote> quotes;

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getCompliant() {
        return compliant;
    }

    public void setCompliant(Boolean compliant) {
        this.compliant = compliant;
    }

    public List<VendorQuote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<VendorQuote> quotes) {
        this.quotes = quotes;
    }
}
