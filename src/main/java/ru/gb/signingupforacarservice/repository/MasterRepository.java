package ru.gb.signingupforacarservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.signingupforacarservice.model.Master;

import java.util.List;

/**
 * Интерфейс для взаимодействия с мастерами, занесенными в БД
 */
@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    List<Master> findMasterByLastNameAndFistNameAndMiddleNameAndSpecialization(
            String lastName,
            String firstName,
            String middleName,
            String specialization
    );
}
