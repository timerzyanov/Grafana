package ru.example.SchedulerYandexTaxi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.SchedulerYandexTaxi.apiclient.TaxiApiClient;
import ru.example.SchedulerYandexTaxi.model.Coordinate;
import ru.example.SchedulerYandexTaxi.model.MomentPrice;
import ru.example.SchedulerYandexTaxi.model.Price;
import ru.example.SchedulerYandexTaxi.properties.YandexProperties;
import ru.example.SchedulerYandexTaxi.repository.PriceRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class TaxiService {

    private final YandexProperties yandexProperties;
    private final TaxiApiClient taxiApiClient;
    private final PriceRepository priceRepository;

    public void getPrice(Coordinate startPoint, Coordinate endPoint) {

        String rll = startPoint.toString() + "~" + endPoint.toString();
        String clid = yandexProperties.getClid();
        String apiKey = yandexProperties.getApiKey();

        Price currentPrice = taxiApiClient.getPrice(clid, apiKey, rll);
        if(currentPrice.getOptions().isEmpty()){
            throw new RuntimeException("Options empty");
        }

        double priceDouble = currentPrice.getOptions().get(0).getPrice();

        MomentPrice momentPrice = new MomentPrice(
                LocalDateTime.now(ZoneId.of("Asia/Erevan")),
                priceDouble
        );
        priceRepository.save(momentPrice);
    }
}
