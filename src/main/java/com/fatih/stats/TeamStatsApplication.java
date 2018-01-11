package com.fatih.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class TeamStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamStatsApplication.class, args);
	}

	@Bean(name = "processExecutor")
	public TaskExecutor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(40);
		executor.setQueueCapacity(20000);
		executor.setThreadNamePrefix("TeamStats-");
		executor.initialize();
		return executor;
	}
}
