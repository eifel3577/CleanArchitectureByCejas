package com.example.cleanarchitecturebycejas.Presentation.DI;

/**
 * Интерфейс для классов,которые содержат компонент для DI
 */
public interface HasComponent<C> {
    C getComponent();
}