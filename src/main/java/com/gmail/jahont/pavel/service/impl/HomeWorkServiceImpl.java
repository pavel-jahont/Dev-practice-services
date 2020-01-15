package com.gmail.jahont.pavel.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gmail.jahont.pavel.service.HomeWorkService;
import com.gmail.jahont.pavel.service.model.Car;
import com.gmail.jahont.pavel.service.model.CarModelEnum;
import com.gmail.jahont.pavel.service.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeWorkServiceImpl implements HomeWorkService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static void createDirectoriesChain(File newFile) {
        try {
            if (!newFile.exists()) {
                boolean isCreated = newFile.mkdir();
                if (!isCreated) {
                    isCreated = newFile.mkdirs();
                    if (!isCreated) {
                        throw new IllegalAccessException("Cannot create directory");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new IllegalStateException("Cannot create directory chain");
        }
    }

    @Override
    public void runFirstTask() {
        int elementX = RandomUtil.getElement();
        int elementY = RandomUtil.getElement();
        int elementZ = RandomUtil.getElement();

        if (elementX > elementZ) {
            int sumElements = elementX + elementY;
            logger.debug("Sum x + y :" + sumElements);
        } else {
            logger.debug("Z :" + elementZ);
        }

        int average = (elementX + elementY + elementZ) / 3;
        logger.info("Average : " + average);
    }

    @Override
    public void runSecondTask() {
        int countOfElements = 10;
        int randomNumberRange = 300;
        int[] array = RandomUtil.getArrayInRange(randomNumberRange, countOfElements);
        logger.info(Arrays.toString(array));

        int maxElementIndex = 0;
        int minElementIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[maxElementIndex] < array[i]) {
                maxElementIndex = i;
            }
            if (array[minElementIndex] > array[i]) {
                minElementIndex = i;
            }
        }
        logger.info("Max element: " + array[maxElementIndex]);
        logger.info("Min element: " + array[minElementIndex]);

        array[maxElementIndex] = array[maxElementIndex] * array[minElementIndex];
        logger.error(Arrays.toString(array));
    }

    @Override
    public void runThirdTask() {
        int countOfCars = 10;
        List<Car> cars = generateCarList(countOfCars);
        Map<Integer, List<Car>> mapOfCars = cars.stream().collect(Collectors.groupingBy(Car::getEngineCapacity));
        int engineCapacity = RandomUtil.getElement(0, 5);
        List<Car> carListForEngineCapacity = mapOfCars.get(engineCapacity);
        if (carListForEngineCapacity != null) {
            File testFolder = new File("test");
            createDirectoriesChain(testFolder);

            String fileLocation = "test/output.txt";
            File outputFile = new File(fileLocation);

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile))) {
                bufferedWriter.write("<name> : <carMode> : engineCapacity");
                bufferedWriter.write(System.lineSeparator());
                for (Car car : carListForEngineCapacity) {
                    bufferedWriter.write(car.toString());
                    bufferedWriter.write(System.lineSeparator());
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalStateException("Something wrong with file:" + fileLocation);
            }
        } else {
            logger.debug("Car list with capacity: " + engineCapacity + " does not exists");
        }
    }

    private List<Car> generateCarList(int countOfCars) {
        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < countOfCars; i++) {
            Car car = new Car();
            car.setName("Car" + i);
            CarModelEnum carModel = generateCarModel();
            car.setCarModel(carModel);
            int engineCapacity = generateEngineCapacity();
            car.setEngineCapacity(engineCapacity);

            cars.add(car);
        }
        return cars;
    }

    private int generateEngineCapacity() {
        int minValue = 1;
        int maxValue = 3;
        return RandomUtil.getElement(minValue, maxValue);
    }

    private CarModelEnum generateCarModel() {
        int index = RandomUtil.getElement(0, CarModelEnum.values().length - 1);
        return CarModelEnum.values()[index];
    }
}