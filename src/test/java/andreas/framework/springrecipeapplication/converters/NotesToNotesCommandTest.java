package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.NotesCommand;
import andreas.framework.springrecipeapplication.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesToNotesCommandTest {
    public static final Long ID = new Long(1L);
    public static final String RECIPENOTES = "Hello from Recipe Notes";

    NotesToNotesCommand converter;

    @BeforeEach
    public void setUp(){
        converter = new NotesToNotesCommand();
    }
    @Test
    public void testNullParameters(){
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new Notes()));
    }
    @Test
    public void convert(){
        // Given
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(RECIPENOTES);

        // When
        NotesCommand notesCommand = converter.convert(notes);

        // Then
        assertEquals(ID, notesCommand.getId());
        assertEquals(RECIPENOTES, notesCommand.getRecipeNotes());
    }
}
