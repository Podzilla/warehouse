package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Packager;
import com.podzilla.warehouse.Repositories.PackagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackagerServiceTests {

    @Mock
    private PackagerRepository repository;

    @InjectMocks
    private PackagerService service;

    @Test
    void testCreatePackager() {
        Packager packager = new Packager();
        packager.setName("John");
        when(repository.save(packager)).thenReturn(packager);

        Optional<Packager> saved = service.createPackager(packager);
        assertEquals("John", saved.get().getName());
        verify(repository, times(1)).save(packager);
    }

    @Test
    void testGetPackagerById() {
        Packager packager = new Packager();
        packager.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(packager));

        Optional<Packager> result = service.getPackagerById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testUpdatePackager() {
        Packager existing = new Packager();
        existing.setId(1L);
        existing.setName("Old");

        Packager updated = new Packager();
        updated.setName("New");
        updated.setActive(true);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Packager.class))).thenReturn(existing);

        Optional<Packager> result = service.updatePackager(1L, updated);
        assertEquals("New", result.get().getName());
        assertTrue(result.get().isActive());
    }
}
