package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Hoa_don;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Hoa_don entity.
 */
@SuppressWarnings("unused")
public interface Hoa_donRepository extends JpaRepository<Hoa_don,Long> {

    @Query("select hoa_don from Hoa_don hoa_don where hoa_don.user.login = ?#{principal.username}")
    List<Hoa_don> findByUserIsCurrentUser();
    @Query("select hoa_don from Hoa_don hoa_don where hoa_don.ten_mon_an = ?1")
    List<Hoa_don> findByMonAnInHoaDon(String ten);
    @Query("select hoa_don from Hoa_don hoa_don where hoa_don.user.login = ?1")
    List<Hoa_don> findHoaDonByUser(String user);
}
