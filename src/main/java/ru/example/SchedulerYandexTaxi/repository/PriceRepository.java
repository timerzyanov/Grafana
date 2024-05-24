package ru.example.SchedulerYandexTaxi.repository;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.SchedulerYandexTaxi.model.MomentPrice;

import java.util.List;

@Repository
public interface PriceRepository extends CrudRepository<MomentPrice, Long> {
    @Timed("gettingPricesFromDB")
    List<MomentPrice> findAll();
}
