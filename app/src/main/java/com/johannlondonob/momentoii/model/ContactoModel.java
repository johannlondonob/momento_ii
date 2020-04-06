package com.johannlondonob.momentoii.model;

import java.io.Serializable;

public class ContactoModel implements Serializable {
    private String _id;
    private String _idEntidad;
    private String _cargo;
    private String _nombresApellidos;
    private String _telefonoUno;
    private String _telefonoDos;

    public ContactoModel() {
    }

    public ContactoModel(String _id, String _idEntidad, String _nombresApellidos, String _telefonoUno) {
        this._id = _id;
        this._idEntidad = _idEntidad;
        this._nombresApellidos = _nombresApellidos;
        this._telefonoUno = _telefonoUno;
    }

    public ContactoModel(String _id, String _idEntidad, String _nombresApellidos, String _telefonoUno, String _telefonoDos) {
        this._id = _id;
        this._idEntidad = _idEntidad;
        this._nombresApellidos = _nombresApellidos;
        this._telefonoUno = _telefonoUno;
        this._telefonoDos = _telefonoDos;
    }

    public ContactoModel(String _id, String _idEntidad, String _cargo, String _nombresApellidos, String _telefonoUno, String _telefonoDos) {
        this._id = _id;
        this._idEntidad = _idEntidad;
        this._cargo = _cargo;
        this._nombresApellidos = _nombresApellidos;
        this._telefonoUno = _telefonoUno;
        this._telefonoDos = _telefonoDos;
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

    public String get_cargo() {
        return _cargo;
    }

    public void set_cargo(String _cargo) {
        this._cargo = _cargo;
    }

    public String get_nombresApellidos() {
        return _nombresApellidos;
    }

    public void set_nombresApellidos(String _nombresApellidos) {
        this._nombresApellidos = _nombresApellidos;
    }

    public String get_telefonoUno() {
        return _telefonoUno;
    }

    public void set_telefonoUno(String _telefonoUno) {
        this._telefonoUno = _telefonoUno;
    }

    public String get_telefonoDos() {
        return _telefonoDos;
    }

    public void set_telefonoDos(String _telefonoDos) {
        this._telefonoDos = _telefonoDos;
    }
}
