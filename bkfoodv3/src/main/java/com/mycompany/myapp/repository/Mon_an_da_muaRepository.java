package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mon_an_da_mua;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mon_an_da_mua entity.
 */
@SuppressWarnings("unused")
public interface Mon_an_da_muaRepository extends JpaRepository<Mon_an_da_mua,Long> {

    @Query("select mon_an_da_mua from Mon_an_da_mua mon_an_da_mua where mon_an_da_mua.user.login = ?#{principal.username}")
    List<Mon_an_da_mua> findByUserIsCurrentUser();

}
