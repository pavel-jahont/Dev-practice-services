package com.gmail.jahont.pavel;

import com.gmail.jahont.pavel.service.HomeWorkService;
import com.gmail.jahont.pavel.service.impl.HomeWorkServiceImpl;

public class App {

    public static void main(String[] args) {
        HomeWorkService homeWorkService = new HomeWorkServiceImpl();
        homeWorkService.runFirstTask();
        homeWorkService.runSecondTask();
        homeWorkService.runThirdTask();
    }
}
