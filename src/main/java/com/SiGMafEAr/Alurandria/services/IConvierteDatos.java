package com.SiGMafEAr.Alurandria.services;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}