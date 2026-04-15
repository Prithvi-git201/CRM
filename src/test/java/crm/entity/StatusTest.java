package crm.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void testStatusEnumValues() {
        // Assert
        assertNotNull(Status.PROPOSED);
        assertNotNull(Status.NEGOTIATED);
        assertNotNull(Status.IMPLEMENTED);
        assertNotNull(Status.DONE);
    }

    @Test
    void testStatusAllArray() {
        // Act
        Status[] allStatuses = Status.ALL;
        
        // Assert
        assertNotNull(allStatuses);
        assertEquals(4, allStatuses.length);
        assertEquals(Status.PROPOSED, allStatuses[0]);
        assertEquals(Status.NEGOTIATED, allStatuses[1]);
        assertEquals(Status.IMPLEMENTED, allStatuses[2]);
        assertEquals(Status.DONE, allStatuses[3]);
    }

    @Test
    void testStatusValueOf() {
        // Act & Assert
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"));
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"));
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"));
        assertEquals(Status.DONE, Status.valueOf("DONE"));
    }

    @Test
    void testStatusValueOfInvalid() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Status.valueOf("INVALID");
        });
    }

    @Test
    void testStatusValues() {
        // Act
        Status[] values = Status.values();
        
        // Assert
        assertNotNull(values);
        assertEquals(4, values.length);
    }

    @Test
    void testStatusToString() {
        // Act & Assert
        assertEquals("PROPOSED", Status.PROPOSED.toString());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.toString());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.toString());
        assertEquals("DONE", Status.DONE.toString());
    }

    @Test
    void testStatusEquality() {
        // Assert
        assertEquals(Status.PROPOSED, Status.PROPOSED);
        assertNotEquals(Status.PROPOSED, Status.DONE);
    }

    @Test
    void testStatusOrdinal() {
        // Assert
        assertEquals(0, Status.PROPOSED.ordinal());
        assertEquals(1, Status.NEGOTIATED.ordinal());
        assertEquals(2, Status.IMPLEMENTED.ordinal());
        assertEquals(3, Status.DONE.ordinal());
    }
}
