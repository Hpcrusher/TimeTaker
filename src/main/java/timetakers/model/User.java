/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.model;

import timetakers.util.EncryptPassword;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author David Liebl
 */

@Entity
@Table(name = "user")
public class User extends AbstractIdEntity {

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_has_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = EncryptPassword.encrypt(password);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String userName;
        private String password;
        private Person person;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setPerson(Person person) {
            this.person = person;
            return this;
        }

        public User createUser() {
            User user = new User();
            user.setId(id);
            user.setPerson(person);
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
    }
}
