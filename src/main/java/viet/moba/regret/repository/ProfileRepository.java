package viet.moba.regret.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import viet.moba.regret.entity.profile.Profile;
import viet.moba.regret.entity.profile.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByUserId(String id);
    List<Profile> findAllByStatus(Status status);
}
