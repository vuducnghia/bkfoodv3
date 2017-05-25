package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Mon_an.
 */
@Entity
@Table(name = "mon_an")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mon_an implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ten", nullable = false)
    private String ten;

    @NotNull
    @Column(name = "gia_tien", nullable = false)
    private Double gia_tien;

    @NotNull
    @Column(name = "the_loai", nullable = false)
    private String the_loai;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Lob
    @Column(name = "hinh_anh", nullable = false)
    private byte[] hinh_anh;

    @Column(name = "hinh_anh_content_type", nullable = false)
    private String hinh_anhContentType;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "sale", nullable = false)
    private Integer sale;

    @NotNull
    @Column(name = "link_comment_facebook", nullable = false)
    private String link_comment_facebook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public Mon_an ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Double getGia_tien() {
        return gia_tien;
    }

    public Mon_an gia_tien(Double gia_tien) {
        this.gia_tien = gia_tien;
        return this;
    }

    public void setGia_tien(Double gia_tien) {
        this.gia_tien = gia_tien;
    }

    public String getThe_loai() {
        return the_loai;
    }

    public Mon_an the_loai(String the_loai) {
        this.the_loai = the_loai;
        return this;
    }

    public void setThe_loai(String the_loai) {
        this.the_loai = the_loai;
    }

    public LocalDate getDate() {
        return date;
    }

    public Mon_an date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getHinh_anh() {
        return hinh_anh;
    }

    public Mon_an hinh_anh(byte[] hinh_anh) {
        this.hinh_anh = hinh_anh;
        return this;
    }

    public void setHinh_anh(byte[] hinh_anh) {
        this.hinh_anh = hinh_anh;
    }

    public String getHinh_anhContentType() {
        return hinh_anhContentType;
    }

    public Mon_an hinh_anhContentType(String hinh_anhContentType) {
        this.hinh_anhContentType = hinh_anhContentType;
        return this;
    }

    public void setHinh_anhContentType(String hinh_anhContentType) {
        this.hinh_anhContentType = hinh_anhContentType;
    }

    public Integer getSale() {
        return sale;
    }

    public Mon_an sale(Integer sale) {
        this.sale = sale;
        return this;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public String getLink_comment_facebook() {
        return link_comment_facebook;
    }

    public Mon_an link_comment_facebook(String link_comment_facebook) {
        this.link_comment_facebook = link_comment_facebook;
        return this;
    }

    public void setLink_comment_facebook(String link_comment_facebook) {
        this.link_comment_facebook = link_comment_facebook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mon_an mon_an = (Mon_an) o;
        if (mon_an.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mon_an.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mon_an{" +
            "id=" + id +
            ", ten='" + ten + "'" +
            ", gia_tien='" + gia_tien + "'" +
            ", the_loai='" + the_loai + "'" +
            ", date='" + date + "'" +
            ", hinh_anh='" + hinh_anh + "'" +
            ", hinh_anhContentType='" + hinh_anhContentType + "'" +
            ", sale='" + sale + "'" +
            ", link_comment_facebook='" + link_comment_facebook + "'" +
            '}';
    }
}
