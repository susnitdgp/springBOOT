package com.kaniha.gatepass.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.kaniha.gatepass.model.PersonForm;


@Component
public class PersonFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		
		return PersonForm.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		PersonForm person=(PersonForm)target;
		
		 ValidationUtils.
		 rejectIfEmptyOrWhitespace(errors, "name", "name", "First name is required.");
		 
		 ValidationUtils.
		 rejectIfEmptyOrWhitespace(errors, "age", "age", "Age is required.");
		
		if(person.getAge() !=null){
			
			int age=person.getAge();
			
			if(age < 18){
				
				errors.rejectValue("age", "age.invalid", "Age canot be less than 18");
			}
			
		}
				
		if(person.getFile() != null){
			
			MultipartFile file=person.getFile();
			
			if(file.getSize()>0){
				
			}else{
				
				errors.rejectValue("file", "file.invalid", "File can not be blank");
			}
			
			
		}
			
	
	}

}
