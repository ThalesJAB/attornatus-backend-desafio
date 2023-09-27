package br.com.attornatusdesafio.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	

	@Bean
	public ModelMapper mapper() {

		ModelMapper modelMapper = new ModelMapper();;


		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
/*
		modelMapper.createTypeMap(ProfileType.class, Integer.class)
				.setConverter(context -> {
					ProfileType profileType = context.getSource();
					return profileType.getCode();
				});
				*/

		return modelMapper;
		
	}

}
