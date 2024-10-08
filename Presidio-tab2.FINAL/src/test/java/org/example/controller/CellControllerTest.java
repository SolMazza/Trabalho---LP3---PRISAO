package org.example.controller;

import org.example.exception.NoneCellFind;
import org.example.model.Cell;
import org.example.model.Disability;
import org.example.model.FaccaoLeader;
import org.example.model.Prisoner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CellControllerTest {

    private CellController cellController;
    private List<Cell> cells;
    private Prisoner prisoner;

    @BeforeEach
    void setUp() {
        cellController = new CellController();

        cells = new ArrayList<>();
        cells.add(new Cell("A", 10, 5));
        cells.add(new Cell("B", 7, 3));
        cells.add(new Cell("C", 5, 5));
    }

    @Test
    void testCellHasSpaceForCommonPrisoner() throws NoneCellFind {

        prisoner = new Prisoner("z", 2, Disability.NAO, FaccaoLeader.NAO);

        List<Cell> result = cellController.cellHasSpace(prisoner, cells);

        assertFalse(result.isEmpty(), "Deve haver celas disponíveis para o prisioneiro comum.");
        assertEquals(3, result.size(), "Deve haver 3 celas adequadas para o prisioneiro comum.");
    }

    @Test
    void testCellHasSpaceForDisabledPrisoner() throws NoneCellFind {
        prisoner = new Prisoner("M", 2, Disability.SIM, FaccaoLeader.NAO);

        cells.get(0).allocatePrisoner(new Prisoner("L", 2, Disability.NAO, FaccaoLeader.NAO));

        List<Cell> result = cellController.cellHasSpace(prisoner, cells);

        assertFalse(result.isEmpty(), "Deve haver celas adequadas para prisioneiros com deficiência.");
        assertEquals(1, result.size(), "Deve haver 1 cela adequada com outros prisioneiros.");
    }

    @Test
    void testCellHasSpaceThrowsNoneCellFindException() {
        prisoner = new Prisoner("O", 6, Disability.NAO, FaccaoLeader.NAO);

        Exception exception = assertThrows(NoneCellFind.class, () -> {
            cellController.cellHasSpace(prisoner, cells);
        });

        assertEquals("Não há celas disponíveis.", exception.getMessage());
    }

    @Test
    void testCellHasSpaceForLeaderPrisoner() throws NoneCellFind {
        prisoner = new Prisoner("L", 2, Disability.NAO, FaccaoLeader.SIM);

        List<Cell> result = cellController.cellHasSpace(prisoner, cells);

        assertFalse(result.isEmpty(), "Deve haver celas disponíveis para o líder de facção.");
        assertEquals(3, result.size(), "Deve haver 3 celas adequadas para o líder de facção.");
    }
}
