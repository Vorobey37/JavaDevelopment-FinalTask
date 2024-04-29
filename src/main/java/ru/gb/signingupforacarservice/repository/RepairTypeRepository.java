package ru.gb.signingupforacarservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.signingupforacarservice.model.RepairType;

import java.util.List;

@Repository
public interface RepairTypeRepository extends JpaRepository<RepairType, Long> {

    List<RepairType> findRepairTypeByNameAndDurationTimeAndPrice(String name, float durationTime, double price);
}
