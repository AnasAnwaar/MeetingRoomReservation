package com.example.meetingroomreservation;
public class User {
    private int userId;
    private String name;
    private String employeeId;
    private String department;
    private String designation;
    private String contactNumber;
    private String companyEmail;
    private String password;

    public User(String name, String employeeId, String department, String designation, String contactNumber, String companyEmail, String password) {
        this.name = name;
        this.employeeId = employeeId;
        this.department = department;
        this.designation = designation;
        this.contactNumber = contactNumber;
        this.companyEmail = companyEmail;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
