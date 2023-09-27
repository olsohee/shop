package project.shop.repository;

import org.springframework.data.repository.CrudRepository;
import project.shop.entity.user.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByEmail(String email);
}
