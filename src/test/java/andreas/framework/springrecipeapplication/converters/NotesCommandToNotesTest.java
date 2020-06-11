package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.NotesCommand;
import andreas.framework.springrecipeapplication.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesCommandToNotesTest {

    public static final Long ID = new Long(1L);
    public static final String RECIPE_NOTES = "Hello from recipe Notes";

    NotesCommandToNotes converter;

    @BeforeEach
    public void setUp(){
        converter = new NotesCommandToNotes();
    }
    @Test
    public void testNullParameter(){
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new NotesCommand()));
    }
    @Test
    public void convert(){
        // Given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        // When
        Notes notes = converter.convert(notesCommand);

        // Then
        assertNotNull(notes);
        assertEquals(ID, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}
