package com.wodder.model.users;

import org.apache.commons.lang3.*;

public abstract class User implements Cloneable {

    protected long id;
    protected String fName;
    protected String lName;
    protected String userName;

    public User(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
        createUserName(this.fName, this.lName);
    }

    public String userName() {
        return userName;
    }

    public String fName() {
        return fName;
    }

    public String lName() {
        return lName;
    }

    public void setFirstName(String s) {
        this.fName = s;
    }

    public void setLastName(String s) {
        this.lName = s;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract boolean isManager();

    public abstract String roleName();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!fName.equals(user.fName)) return false;
        return lName.equals(user.lName);
    }

    @Override
    public int hashCode() {
        int result = fName.hashCode();
        result = 31 * result + lName.hashCode();
        return result;
    }

    @Override
    public User clone() {
        return new UserFactory().createUser(this.fName, this.lName, this.roleName());
    }

    private void createUserName(String fname, String lname) {
        if (StringUtils.isNoneBlank(fname, lname)) {
            userName = fname.toLowerCase().charAt(0) + lname.toLowerCase();
        } else {
            throw new IllegalArgumentException("Unable to generate user name");
        }
    }
}
