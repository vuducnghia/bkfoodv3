package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Hoa_don.
 */
@Entity
@Table(name = "hoa_don")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hoa_don implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_mon_an")
    private String ten_mon_an;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "sale")
    private Integer sale;

    @Column(name = "gia_tien")
    private Double gia_tien;

    @Column(name = "so_luong")
    private Integer so_luong;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen_mon_an() {
        return ten_mon_an;
    }

    public Hoa_don ten_mon_an(String ten_mon_an) {
        this.ten_mon_an = ten_mon_an;
        return this;
    }

    public void setTen_mon_an(String ten_mon_an) {
        this.ten_mon_an = ten_mon_an;
    }

    public Integer getSale() {
        return sale;
    }

    public Hoa_don sale(Integer sale) {
        this.sale = sale;
        return this;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Double getGia_tien() {
        return gia_tien;
    }

    public Hoa_don gia_tien(Double gia_tien) {
        this.gia_tien = gia_tien;
        return this;
    }

    public void setGia_tien(Double gia_tien) {
        this.gia_tien = gia_tien;
    }

    public Integer getSo_luong() {
        return so_luong;
    }

    public Hoa_don so_luong(Integer so_luong) {
        this.so_luong = so_luong;
        return this;
    }

    public void setSo_luong(Integer so_luong) {
        this.so_luong = so_luong;
    }

    public User getUser() {
        return user;
    }

    public Hoa_don user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hoa_don hoa_don = (Hoa_don) o;
        if (hoa_don.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hoa_don.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hoa_don{" +
            "id=" + id +
            ", ten_mon_an='" + ten_mon_an + "'" +
            ", sale='" + sale + "'" +
            ", gia_tien='" + gia_tien + "'" +
            ", so_luong='" + so_luong + "'" +
            '}';
    }
}
