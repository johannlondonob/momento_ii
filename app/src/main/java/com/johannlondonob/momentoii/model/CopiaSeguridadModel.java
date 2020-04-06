package com.johannlondonob.momentoii.model;

import java.io.Serializable;

public class CopiaSeguridadModel implements Serializable {
    private String _id;
    private String _idEntidad;
    private String _nombreServicio;
    private String _email;
    private String _clave;
    private String _responsable;
    private String _emailResponsable;
    private String _telefonoResponsable;
    private boolean _activo;

    public CopiaSeguridadModel() {
    }

    public CopiaSeguridadModel(String _id, String _idEntidad, String _nombreServicio, String _email, String _clave, String _responsable, String _emailResponsable, boolean _activo) {
        this._id = _id;
        this._idEntidad = _idEntidad;
        this._nombreServicio = _nombreServicio;
        this._email = _email;
        this._clave = _clave;
        this._responsable = _responsable;
        this._emailResponsable = _emailResponsable;
        this._activo = _activo;
    }

    public CopiaSeguridadModel(String _id, String _idEntidad, String _nombreServicio, String _email, String _clave, String _responsable, String _emailResponsable, String _telefonoResponsable, boolean _activo) {
        this._id = _id;
        this._idEntidad = _idEntidad;
        this._nombreServicio = _nombreServicio;
        this._email = _email;
        this._clave = _clave;
        this._responsable = _responsable;
        this._emailResponsable = _emailResponsable;
        this._telefonoResponsable = _telefonoResponsable;
        this._activo = _activo;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_idEntidad() {
        return _idEntidad;
    }

    public void set_idEntidad(String _idEntidad) {
        this._idEntidad = _idEntidad;
    }

    public String get_nombreServicio() {
        return _nombreServicio;
    }

    public void set_nombreServicio(String _nombreServicio) {
        this._nombreServicio = _nombreServicio;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_clave() {
        return _clave;
    }

    public void set_clave(String _clave) {
        this._clave = _clave;
    }

    public String get_responsable() {
        return _responsable;
    }

    public void set_responsable(String _responsable) {
        this._responsable = _responsable;
    }

    public String get_emailResponsable() {
        return _emailResponsable;
    }

    public void set_emailResponsable(String _emailResponsable) {
        this._emailResponsable = _emailResponsable;
    }

    public String get_telefonoResponsable() {
        return _telefonoResponsable;
    }

    public void set_telefonoResponsable(String _telefonoResponsable) {
        this._telefonoResponsable = _telefonoResponsable;
    }

    public boolean is_activo() {
        return _activo;
    }

    public void set_activo(boolean _activo) {
        this._activo = _activo;
    }
}

