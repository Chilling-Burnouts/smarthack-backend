package com.chillingburnouts.smarthack.entities;

import com.chillingburnouts.smarthack.dtos.Naics;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String companyName;
    private String mainCountry;
    private List<String> businessTags;
    private String longDescription;
    private String shortDescription;

    @ManyToMany(mappedBy = "companies")
    private Set<Portofolio> portofolios = new HashSet<>();

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

    public Set<Portofolio> getUsers() {
        return portofolios;
    }

    public void setUsers(Set<Portofolio> portofolios) {
        this.portofolios = portofolios;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMainCountry() {
        return mainCountry;
    }

    public void setMainCountry(String mainCountry) {
        this.mainCountry = mainCountry;
    }

    public List<String> getBusinessTags() {
        return businessTags;
    }

    public void setBusinessTags(List<String> businessTags) {
        this.businessTags = businessTags;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Set<Portofolio> getPortofolios() {
        return portofolios;
    }

    public void setPortofolios(Set<Portofolio> portofolios) {
        this.portofolios = portofolios;
    }
}
