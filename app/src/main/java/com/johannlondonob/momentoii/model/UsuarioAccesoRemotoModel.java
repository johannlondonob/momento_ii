package com.johannlondonob.momentoii.model;

import java.io.Serializable;

public class UsuarioAccesoRemotoModel implements Serializable {
    private String _id;
    private String _idEntidad;
    private String _idAccesoRemoto;
    private String _nombreUsuario;
    private String _claveUsuario;
    private String _asignadoA;
    private String _telefonoAsignado;
    private boolean _admin;

    public UsuarioAccesoRemotoModel() {}

    public UsuarioAccesoRemotoModel(String _id, String _idEntidad, String _idAccesoRemoto, String _nombreUsuario, String _claveUsuario, String _asignadoA, String _telefonoAsignado, boolean _admin) {
        this._id = _id;
        this._idEntidad = _idEntidad;
        this._idAccesoRemoto = _idAccesoRemoto;
        this._nombreUsuario = _nombreUsuario;
        this._claveUsuario = _claveUsuario;
        this._asignadoA = _asignadoA;
        this._telefonoAsignado = _telefonoAsignado;
        this._admin = _admin;
    }

    public UsuarioAccesoRemotoModel(String _id, String _idEntidad, String _idAccesoRemoto, String _nombreUsuario, String _claveUsuario, boolean _admin) {
        this._id = _id;
        this._idEntidad = _idEntidad;
        this._idAccesoRemoto = _idAccesoRemoto;
        this._nombreUsuario = _nombreUsuario;
        this._claveUsuario = _claveUsuario;
        this._admin = _admin;
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

    public String get_idAccesoRemoto() {
        return _idAccesoRemoto;
    }

    public void set_idAccesoRemoto(String _idAccesoRemoto) {
        this._idAccesoRemoto = _idAccesoRemoto;
    }

    public String get_nombreUsuario() {
        return _nombreUsuario;
    }

    public void set_nombreUsuario(String _nombreUsuario) {
        this._nombreUsuario = _nombreUsuario;
    }

    public String get_claveUsuario() {
        return _claveUsuario;
    }

    public void set_claveUsuario(String _claveUsuario) {
        this._claveUsuario = _claveUsuario;
    }

    public String get_asignadoA() {
        return _asignadoA;
    }

    public void set_asignadoA(String _asignadoA) {
        this._asignadoA = _asignadoA;
    }

    public String get_telefonoAsignado() {
        return _telefonoAsignado;
    }

    public void set_telefonoAsignado(String _telefonoAsignado) {
        this._telefonoAsignado = _telefonoAsignado;
    }

    public boolean get_admin() {
        return _admin;
    }

    public void set_admin(boolean _admin) {
        this._admin = _admin;
    }
}
