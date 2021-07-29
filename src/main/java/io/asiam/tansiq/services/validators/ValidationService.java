package io.asiam.tansiq.services.validators;

import io.asiam.tansiq.exceptions.UserInputException;
import io.validly.excpetion.ValidationErrorException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

import static io.validly.FailFastValidator.valid;

@Service
public class ValidationService {
    public void validatorWrapper(Runnable validationLogic) {
        try {
            validationLogic.run();
        } catch (ValidationErrorException validationErrorException) {
            throw new UserInputException(validationErrorException.getMessage());
        }

    }

    public void validateEmail(String email, Predicate<String> emailUniquenessPredicate) {
        valid(email)
                .mustNotBeNull("Email is required")
                .must(EmailValidator.getInstance()::isValid, "Not a valid email")
                .must(emailUniquenessPredicate, "Email must be unique");
    }

    public void validatePassword(String password) {
        valid(password)
                .mustNotBeNull("Password is required")
                .lengthMustBeAtLeast(8, "Password cannot be shorter than 8 characters")
                .must(p -> p.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"), "Not a strong password");
    }

    public void validateName(String name) {
        valid(name)
                .mustNotBeNull("Name is required")
                .must(n -> n.replace(" ", "").length() > 1 , "Name must be greater than 2 characters")
                .must(n -> n.split(" ").length > 2, "Must be a full-name contains at least 3 names");
    }
    public void validateMark(Integer mark) {
        valid(mark)
                .mustNotBeNull("Mark is required")
                .must(m -> m >= 0, "Mark must be positive");
    }
    public void validateMajorName(String name) {
        valid(name)
                .mustNotBeNull("Major name is required")
                .must(n -> n.length() > 2, "Major name length must be greater than 2");
    }
    public void validateMajorLimit(Integer limit) {
        valid(limit)
                .mustNotBeNull("Major limit must be provided")
                .must(l -> l > 0, "Limit must be positive");
    }
}
