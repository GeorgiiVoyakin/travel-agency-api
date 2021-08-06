package ru.mirea.ikbo1319.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.ikbo1319.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {}
