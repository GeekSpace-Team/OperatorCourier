package com.android.operatorcourier.Model;

public class Login {
    private String id;
    private String fullname;
    private String username;
    private String password;
    private String phone_number;
    private String status;
    private String created_at;
    private String updated_at;
    private String date_of_birthday;
    private String work_start_date;
    private String sell_point_id;
    private String unique_id;
    private String user_role;
    private String token;
    private String role_name;

    public Login(String id, String fullname, String username, String password, String phone_number, String status, String created_at, String updated_at, String date_of_birthday, String work_start_date, String sell_point_id, String unique_id, String user_role, String token, String role_name) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.date_of_birthday = date_of_birthday;
        this.work_start_date = work_start_date;
        this.sell_point_id = sell_point_id;
        this.unique_id = unique_id;
        this.user_role = user_role;
        this.token = token;
        this.role_name = role_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDate_of_birthday() {
        return date_of_birthday;
    }

    public void setDate_of_birthday(String date_of_birthday) {
        this.date_of_birthday = date_of_birthday;
    }

    public String getWork_start_date() {
        return work_start_date;
    }

    public void setWork_start_date(String work_start_date) {
        this.work_start_date = work_start_date;
    }

    public String getSell_point_id() {
        return sell_point_id;
    }

    public void setSell_point_id(String sell_point_id) {
        this.sell_point_id = sell_point_id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
