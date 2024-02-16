package com.dontirikk.profileservice;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestUtils {
    public final UUID STUDENT_ID = UUID.randomUUID();
    public final String STUDENT_NAME = "John Doe";
    public final String STUDENT_EMAIL = "john.doe@email.com";
    public final String STUDENT_UPDATED_EMAIL = "jane.doe@email.com";
}
