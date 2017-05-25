package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mon_an;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mon_an entity.
 */
@SuppressWarnings("unused")
public interface Mon_anRepository extends JpaRepository<Mon_an,Long> {
    @Query ("select monan from Mon_an monan where monan.the_loai =?1")
    public List<Mon_an> findMonAntheloai(String the_loai);
    @Query ("select monan from Mon_an monan order by monan.date DESC")
    public List<Mon_an> findMonAndate();
    @Query("select monan from Mon_an monan where monan.ten like %?1%")
    public List<Mon_an> findMonAnTen(String ten);
}
