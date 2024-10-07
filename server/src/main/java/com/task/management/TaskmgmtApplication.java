package com.task.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootApplication
public class TaskmgmtApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(TaskmgmtApplication.class, args);
	}

	/**
	 *  method to throw error when database is not available to connect
	 *
	 * @param args String
	 * @throws Exception e
	 */
	public void run(String... args) throws Exception{
		try {
			if (jdbcTemplate == null || jdbcTemplate.getDataSource() == null ||
					jdbcTemplate.getDataSource().getConnection() == null || jdbcTemplate.getDataSource().getConnection().isClosed()) {
				log.debug("database connection is not available");
				log.info("shutting down the application");
				SpringApplication.exit(applicationContext,()->404);
			}
			else{
				log.info("\n####################################################################\n----------------------- Application Started ------------------------\n####################################################################");
			}
		}
		catch (Exception e){
			log.error("Error while connecting database.");
			throw e;
		}
	}


}
