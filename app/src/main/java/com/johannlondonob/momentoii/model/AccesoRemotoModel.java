package com.johannlondonob.momentoii.model;

import java.io.Serializable;

public class AccesoRemotoModel implements Serializable {
    private String _id;
    private String _idEntidad;
    private String _nombreEquipo;
    private String _ipPublica;
    private String _ipPrivada;

    public AccesoRemotoModel() {
    }

    public AccesoRemotoModel(String _id, String _idEntidad, String _nombreEquipo, String _ipPublica, String _ipPrivada) {
        this._id = _id;
        this._idEntidad = _idEntidad;
        this._nombreEquipo = _nombreEquipo;
        this._ipPublica = _ipPublica;
        this._ipPrivada = _ipPrivada;
    }

    public String get_idEntidad() {
        return _idEntidad;
    }

    public void set_idEntidad(String _idEntidad) {
        this._idEntidad = _idEntidad;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_nombreEquipo() {
        return _nombreEquipo;
    }

    public void set_nombreEquipo(String _nombreEquipo) {
        this._nombreEquipo = _nombreEquipo;
    }

    public String get_ipPublica() {
        return _ipPublica;
    }

    public void set_ipPublica(String _ipPublica) {
        this._ipPublica = _ipPublica;
    }

    public String get_ipPrivada() {
        return _ipPrivada;
    }

    public void set_ipPrivada(String _ipPrivada) {
        this._ipPrivada = _ipPrivada;
    }
}
