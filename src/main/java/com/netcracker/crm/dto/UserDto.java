package com.netcracker.crm.dto;

/**
 * Created by bpogo on 4/30/2017.
 */
public class UserDto {
    private Long id;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String email;
    private boolean contactPerson;
    private String userRole;
    private String organizationName;
    private Double addressLatitude;
    private Double addressLongitude;
    private String addressDetails;
    private String addressRegionName;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(boolean contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Double getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(Double addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public Double getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(Double addressLongitude) {
        this.addressLongitude = addressLongitude;
    }


    public String getAddressRegionName() {
        return addressRegionName;
    }

    public void setAddressRegionName(String addressRegionName) {
        this.addressRegionName = addressRegionName;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }
}
