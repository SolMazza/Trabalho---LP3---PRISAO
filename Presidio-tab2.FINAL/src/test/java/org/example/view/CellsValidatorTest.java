package org.example.view;

import org.example.controller.CellController;
import org.example.exception.NoneCellFind;
import org.example.exception.NonePrisoner;
import org.example.factory.CellFactory;
import org.example.factory.PrisonerFactory;
import org.example.model.Cell;
import org.example.model.Prisoner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CellsValidatorTest {


    @Mock
    private CellController controller;

    @Mock
    private PrisonerFactory prisonerFactory;

    @Mock
    private CellFactory cellFactory;
    @InjectMocks
    private CellsValidator cellsValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPrisionerByNameFound() throws NonePrisoner {
        List<Prisoner> prisoners = new ArrayList<>();
        prisoners.add(new Prisoner("Z", 2, null, null));
        prisoners.add(new Prisoner("M", 3, null, null));

        Prisoner result = cellsValidator.prisionerByName(prisoners, "Z");
        assertNotNull(result);
        assertEquals("Z", result.getName());
    }

    @Test
    void testPrisionerByNameNotFound() {
        List<Prisoner> prisoners = new ArrayList<>();
        prisoners.add(new Prisoner("Z", 2, null, null));
        prisoners.add(new Prisoner("M", 3, null, null));

        assertThrows(NonePrisoner.class, () -> {
            cellsValidator.prisionerByName(prisoners, "Y");
        });
    }

    @Test
    void testCellByNameFound() throws NoneCellFind {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell("Cela A", 5, 5));
        cells.add(new Cell("Cela B", 10, 10));

        Cell result = cellsValidator.cellByName(cells, "Cela B");
        assertNotNull(result);
        assertEquals("Cela B", result.getName());
    }

    @Test
    void testCellByNameNotFound() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell("Cela A", 5, 5));
        cells.add(new Cell("Cela B", 10, 10));

        assertThrows(NoneCellFind.class, () -> {
            cellsValidator.cellByName(cells, "Cela C");
        });
    }

    @Test
    void testAlocarPrisioneiro() {
        List<Prisoner> prisoners = new ArrayList<>();
        prisoners.add(new Prisoner("Z", 2, null, null));

        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell("Cela A", 5, 5));

        when(prisonerFactory.getPrisoners()).thenReturn((ArrayList<Prisoner>) prisoners);
        when(cellFactory.getCells()).thenReturn((ArrayList<Cell>) cells);
        when(controller.cellHasSpace(any(Prisoner.class), anyList())).thenReturn(cells);
        when(controller.allocatePrisonerToCell(any(Prisoner.class), any(Cell.class), anyList())).thenReturn(true);

        cellsValidator.alocarPrisioneiro();

        verify(controller, times(1)).allocatePrisonerToCell(any(Prisoner.class), any(Cell.class), anyList());
    }
}
