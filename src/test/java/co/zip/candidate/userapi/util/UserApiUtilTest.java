package co.zip.candidate.userapi.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserApiUtilTest {

    @Test
    void getErrorResponseTest() {
        Assertions.assertEquals(false, UserApiUtil.getErrorResponse("500","").getSuccess());
    }
}
