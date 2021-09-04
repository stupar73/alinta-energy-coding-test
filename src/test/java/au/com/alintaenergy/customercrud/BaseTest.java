package au.com.alintaenergy.customercrud;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BaseTest {
    protected <T extends Throwable> void assertThrowsInCausesStack(Class<T> expectedType, Executable executable) {
        Throwable t = assertThrows(Exception.class, executable);

        List<Throwable> exceptions = new ArrayList<>();
        exceptions.add(t);
        while (t.getCause() != null) {
            t = t.getCause();
            exceptions.add(t);
        }

        assertTrue(exceptions.stream().anyMatch(expectedType::isInstance));
    }
}
