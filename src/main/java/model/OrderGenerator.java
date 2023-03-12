package model;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class OrderGenerator {

    public static Order getRandom(String[] colors) {
        Random random = new Random();
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        String metroStation = RandomStringUtils.randomAlphabetic(10);
        String phone = RandomStringUtils.randomAlphabetic(10);
        int rentTime = random.nextInt(20);
        String deliveryDate = RandomStringUtils.randomAlphabetic(10);
        String comment = RandomStringUtils.randomAlphabetic(10);
        String[] color = colors;
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
