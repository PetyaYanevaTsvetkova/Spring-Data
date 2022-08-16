package com.example.demo.model.entity;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private Boolean consentStatus;
    private Boolean isProfileActive;
    private LocalDate dateProfileCreated;
    private LocalDate dateProfileDeactivated;
    private String reasonForDeactivation;
    private String notes;
    private Set<Orders> orders;
    private Address address;

    public Customer() {
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "consent_status", nullable = false)
    public Boolean getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(Boolean consentStatus) {
        this.consentStatus = consentStatus;
    }

    @Column(name = "is_profile_active", nullable = false)
    public Boolean getProfileActive() {
        return isProfileActive;
    }

    public void setProfileActive(Boolean profileActive) {
        isProfileActive = profileActive;
    }

    @Column(name = "date_profile_created", nullable = false)
    public LocalDate getDateProfileCreated() {
        return dateProfileCreated;
    }

    public void setDateProfileCreated(LocalDate dateProfileCreated) {
        this.dateProfileCreated = dateProfileCreated;
    }

    @Column(name = "date_profile_deactivated")
    public LocalDate getDateProfileDeactivated() {
        return dateProfileDeactivated;
    }

    public void setDateProfileDeactivated(LocalDate dateProfileDeactivated) {
        this.dateProfileDeactivated = dateProfileDeactivated;
    }

    @Column(name = "reason_for_deactivation")
    public String getReasonForDeactivation() {
        return reasonForDeactivation;
    }

    public void setReasonForDeactivation(String reasonForDeactivation) {
        this.reasonForDeactivation = reasonForDeactivation;
    }

    @Column(name = "notes", columnDefinition ="TEXT")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }
}
