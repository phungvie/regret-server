package viet.moba.regret.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import viet.moba.regret.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {}
