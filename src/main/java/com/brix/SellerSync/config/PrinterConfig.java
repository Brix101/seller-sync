package com.brix.SellerSync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brix.SellerSync.color.BluePrinter;
import com.brix.SellerSync.color.ColorPrinter;
import com.brix.SellerSync.color.GreenPrinter;
import com.brix.SellerSync.color.RedPrinter;
import com.brix.SellerSync.color.impl.ColorPrinterImpl;
import com.brix.SellerSync.color.impl.EnglishBluePrinter;
import com.brix.SellerSync.color.impl.EnglishGreenPrinter;
import com.brix.SellerSync.color.impl.EnglishRedPrinter;

import lombok.extern.java.Log;

@Configuration
@Log
public class PrinterConfig {

    @Bean
    public RedPrinter beanRedPrinter() {
        return new EnglishRedPrinter();
    }

    @Bean
    public GreenPrinter beanGreenPrinter() {
        return new EnglishGreenPrinter();
    }

    @Bean
    public BluePrinter beanBluePrinter() {
        return new EnglishBluePrinter();
    }

    @Bean
    public ColorPrinter colorPrinter(RedPrinter redPrinter, GreenPrinter greenPrinter, BluePrinter bluePrinter) {
        log.info("ColorPrinterConfig");
        return new ColorPrinterImpl(redPrinter, greenPrinter, bluePrinter);
    }

}
