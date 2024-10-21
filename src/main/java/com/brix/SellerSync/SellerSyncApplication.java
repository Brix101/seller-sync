package com.brix.SellerSync;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import com.brix.SellerSync.color.ColorPrinter;
import com.brix.SellerSync.color.impl.ColorPrinterImpl;

import lombok.extern.java.Log;


@SpringBootApplication
@Log
public class SellerSyncApplication implements CommandLineRunner{
	
	private final DataSource dataSource;

	public SellerSyncApplication(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(SellerSyncApplication.class, args);
	}
	
	@Override
	public void run(final String... args){
		final ColorPrinter colorPrinter = new ColorPrinterImpl();

		log.info("Color: " + colorPrinter.print());

		log.info("DataSource: " + dataSource.toString());
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");
	}
}
