package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.RefreshToken;
import project.shop.entity.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    public Optional<RefreshToken> findByUser(User user);
}
