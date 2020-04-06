package com.johannlondonob.momentoii.model;

import java.io.Serializable;

public class EntidadModel implements Serializable {
    private String _id;
    private String _nombre;
    private String _direccion;
    private String _telefonoUno;
    private String _telefonoDos;
    private String _email;

    public EntidadModel() {
    }

    public EntidadModel(String _id, String _nombre, String _direccion, String _telefonoUno, String _telefonoDos, String _email) {
        this._id = _id;
        this._nombre = _nombre;
        this._direccion = _direccion;
        this._telefonoUno = _telefonoUno;
        this._telefonoDos = _telefonoDos;
        this._email = _email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_direccion() {
        return _direccion;
    }

    public void set_direccion(String _direccion) {
        this._direccion = _direccion;
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

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }
}
