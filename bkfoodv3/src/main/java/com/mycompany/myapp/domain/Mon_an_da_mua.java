package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Mon_an_da_mua.
 */
@Entity
@Table(name = "mon_an_da_mua")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mon_an_da_mua implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_mon_an")
    private String ten_mon_an;

    @Column(name = "so_luong")
    private Integer so_luong;

    @Column(name = "sale")
    private Integer sale;

    @Column(name = "gia_tien")
    private Double gia_tien;

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

    public Mon_an_da_mua ten_mon_an(String ten_mon_an) {
        this.ten_mon_an = ten_mon_an;
        return this;
    }

    public void setTen_mon_an(String ten_mon_an) {
        this.ten_mon_an = ten_mon_an;
    }

    public Integer getSo_luong() {
        return so_luong;
    }

    public Mon_an_da_mua so_luong(Integer so_luong) {
        this.so_luong = so_luong;
        return this;
    }

    public void setSo_luong(Integer so_luong) {
        this.so_luong = so_luong;
    }

    public Integer getSale() {
        return sale;
    }

    public Mon_an_da_mua sale(Integer sale) {
        this.sale = sale;
        return this;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Double getGia_tien() {
        return gia_tien;
    }

    public Mon_an_da_mua gia_tien(Double gia_tien) {
        this.gia_tien = gia_tien;
        return this;
    }

    public void setGia_tien(Double gia_tien) {
        this.gia_tien = gia_tien;
    }

    public User getUser() {
        return user;
    }

    public Mon_an_da_mua user(User user) {
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
        Mon_an_da_mua mon_an_da_mua = (Mon_an_da_mua) o;
        if (mon_an_da_mua.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mon_an_da_mua.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mon_an_da_mua{" +
            "id=" + id +
            ", ten_mon_an='" + ten_mon_an + "'" +
            ", so_luong='" + so_luong + "'" +
            ", sale='" + sale + "'" +
            ", gia_tien='" + gia_tien + "'" +
            '}';
    }
}
