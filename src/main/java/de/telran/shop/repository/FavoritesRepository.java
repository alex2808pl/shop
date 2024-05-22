package de.telran.shop.repository;

import de.telran.shop.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites,Long> {
}
