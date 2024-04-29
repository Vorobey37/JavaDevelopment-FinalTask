package ru.gb.signingupforacarservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.signingupforacarservice.model.CarServiceClient;

@Repository
public interface ClientRepository extends JpaRepository<CarServiceClient, Long> {

}
