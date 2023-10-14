package uz.supersite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.supersite.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
