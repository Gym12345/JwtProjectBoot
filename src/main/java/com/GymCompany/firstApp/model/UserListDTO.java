package com.GymCompany.firstApp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERLIST")
public class UserListDTO implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userListSeqGen")
    @SequenceGenerator(name = "userListSeqGen", sequenceName = "user_list_seq", allocationSize = 1)
    @Column(name = "ULID")
    private int ulid;

    @Column(name = "USERID", unique = true)
    @NotNull
    @Size(max = 50)
    private String userId;

    @Column(name = "USERPW")
    @NotNull
    @Size(max = 100)
    private String userPw;

    @Column(name = "USERNAME")
    @NotNull
    @Size(max = 100)
    private String userName;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "Last_Login_Time")
    private LocalDateTime lastLoginTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_AUTHORITY", joinColumns = @JoinColumn(name = "UA_ID"))  //USER_AUTHORITY 의 있는  fk 로쓰인 컬럼명말하는거
    @Column(name = "AUTHORITY_NAME")
    private List<String> roles = new ArrayList<>();

    public UserListDTO() {}

    public UserListDTO(int ulid, @NotNull @Size(max = 50) String userId, @NotNull @Size(max = 100) String userPw,
                       @NotNull @Size(max = 100) String userName, LocalDate joinDate, LocalDateTime lastLoginTime,
                       List<String> roles) {
        this.ulid = ulid;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.joinDate = joinDate;
        this.lastLoginTime = lastLoginTime;
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    public int getUlid() {
        return ulid;
    }

    public void setUlid(int ulid) {
        this.ulid = ulid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserListDTO [ulid=" + ulid + ", userId=" + userId + ", userPw=" + userPw + ", userName=" + userName
                + ", joinDate=" + joinDate + ", lastLoginTime=" + lastLoginTime + ", roles=" + roles + "]";
    }

    // UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    // UserDetails ends

    // Builder pattern implementation
    public static class Builder {
        private int ulid;
        private String userId;
        private String userPw;
        private String userName;
        private LocalDate joinDate;
        private LocalDateTime lastLoginTime;
        private List<String> roles;

        public Builder() {}

        public Builder ulid(int ulid) {
            this.ulid = ulid;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userPw(String userPw) {
            this.userPw = userPw;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder joinDate(LocalDate joinDate) {
            this.joinDate = joinDate;
            return this;
        }

        public Builder lastLoginTime(LocalDateTime lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
            return this;
        }

        public Builder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserListDTO build() {
            return new UserListDTO(ulid, userId, userPw, userName, joinDate, lastLoginTime, roles);
        }
    }
}
